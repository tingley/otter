package net.sundell.otter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.xml.stream.XMLStreamException;

import static net.sundell.otter.TMXEventType.*;
import static net.sundell.otter.TestUtil.*;

import org.custommonkey.xmlunit.XMLAssert;
import org.junit.*;

import net.sundell.otter.BeginTag;
import net.sundell.otter.ComplexContent;
import net.sundell.otter.EndTag;
import net.sundell.otter.Header;
import net.sundell.otter.InlineTag;
import net.sundell.otter.IsolatedTag;
import net.sundell.otter.Note;
import net.sundell.otter.OtterException;
import net.sundell.otter.PairedTag;
import net.sundell.otter.PlaceholderTag;
import net.sundell.otter.Property;
import net.sundell.otter.TMXEvent;
import net.sundell.otter.TMXEventType;
import net.sundell.otter.TMXReader;
import net.sundell.otter.TMXWriter;
import net.sundell.otter.TU;
import net.sundell.otter.TUEvent;
import net.sundell.otter.TUV;
import net.sundell.otter.TUVBuilder;
import net.sundell.otter.TUVContent;
import net.sundell.otter.TextContent;

import static org.junit.Assert.*;

public class TestTMXWriter {

    @Test
    public void testSimple() throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXWriter(w);
        Header header = getHeader()
            .addProperty(new Property("type2", "value2").setEncoding("UTF-8"))
            .addProperty(new Property("type3", "value3").setLang("en-US"))
            .addNote(new Note("note2").setEncoding("UTF-16BE"))
            .addNote(new Note("note3").setLang("de-DE"))
            .build();
        writer.startTMX(header);
        writer.endTMX();
        w.close();
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXReader reader = TMXReader.createTMXReader(r);
        List<TMXEvent> events = readEvents(reader);
        checkEvent(events.get(0), TMXEventType.START_TMX);
        Header rHeader = events.get(0).getHeader();
        assertNotNull(rHeader);
        assertEquals(header.getProperties(), rHeader.getProperties());
        assertEquals(header.getNotes(), rHeader.getNotes());
        assertEquals(header, rHeader);
        checkEvent(events.get(1), END_TMX);
        XMLAssert.assertXMLEqual(getSnippetReader("testSimple"),
                       new InputStreamReader(new FileInputStream(tmp), StandardCharsets.UTF_8));
        tmp.delete();
    }
    
    @Test
    public void testWriterMissingRequiredAttributes() throws Exception {
        TMXWriter writer = TMXWriter.createTMXWriter(new StringWriter());
        expectErrorWritingHeader(writer, getHeader().setCreationTool(null), "creationTool");
        expectErrorWritingHeader(writer, getHeader().setCreationToolVersion(null), "creationVersion");
        expectErrorWritingHeader(writer, getHeader().setSegType(null), "segType");
        expectErrorWritingHeader(writer, getHeader().setTmf(null), "tmf");
        expectErrorWritingHeader(writer, getHeader().setAdminLang(null), "adminLang");
        expectErrorWritingHeader(writer, getHeader().setSrcLang(null), "srcLang");
        expectErrorWritingHeader(writer, getHeader().setDataType(null), "datatype");
    }
    
    private void expectErrorWritingHeader(TMXWriter writer, HeaderBuilder headerBuilder, String missingField)
                                          throws XMLStreamException {
        boolean caughtError = false;
        try {
            Header header = headerBuilder.build();
            writer.startTMX(header);
        }
        catch (OtterException e) {
            caughtError = true;
        }
        assertTrue("Failed to throw error writing a Header that was missing '" + missingField + "'", 
                   caughtError);
    }

    @Test
    public void testWriterMissingRequiredTagAtributes() throws Exception {
        TMXWriter writer = TMXWriter.createTMXWriter(new StringWriter());
        writer.startTMX(getHeader().build());
        testTagTUV(writer, Collections.singletonList(new IsolatedTag()), true); // missing @pos
        // The PairedTag correction code will convert unpaired bpt/ept into it,
        // which makes the check useless unless they're both there!
        List<InlineTag> l = new ArrayList<InlineTag>();
        l.add(new BeginTag(1).setI(PairedTag.NO_VALUE));
        l.add(new EndTag(1).setI(PairedTag.NO_VALUE));
        testTagTUV(writer, l, true); // missing @i
        testTagTUV(writer, Collections.singletonList(new PlaceholderTag()), false);
    }

    @Test
    public void testDuplicateBPTattributeIvalues() throws Exception {
        TMXWriter writer = TMXWriter.createTMXWriter(new StringWriter());
        writer.startTMX(getHeader().build());
        TU tu = new TU();
        Exception exception = null;
        try {
            tu.addTUV(tu.tuvBuilder("en-US")
                    .text("hello ")
                    .bpt(1, "<b>").text("bold").ept(1, "</b>")
                    .text(" and ")
                    .bpt(1, "<i>").text("italic").ept(1, "</i>") // these should cause an error
                    .text(" world.").build());
            writer.writeTu(tu);
        }
        catch (Exception e) {
            exception = e;
        }
        assertNotNull("No exception thrown for duplicate @i values", exception);   
    }

    @Test
    public void testWriteTUPropertiesAndNotes() throws Exception {
        StringWriter sw = new StringWriter();
        TMXWriter writer = TMXWriter.createTMXWriter(sw);
        writer.startTMX(getHeader().build());
        TU tu = new TU();
        tu.addProperty(new Property("test-type-1", "value1"));
        tu.addProperty(new Property("test-type-2", "value2"));
        tu.addNote(new Note("Test note"));
        tu.addTUV(tu.tuvBuilder("en-US").text("hello").build());
        tu.addTUV(tu.tuvBuilder("fr-fr").text("bonjour").build());
        writer.writeTu(tu);
        writer.endTMX();
        XMLAssert.assertXMLEqual(getSnippetReader("testWriteTUPropertiesAndNotes"), new StringReader(sw.toString()));
    }

    private void testTagTUV(TMXWriter writer, Collection<? extends InlineTag> tags, boolean expectFailure)
                                        throws XMLStreamException {
        TU tu = new TU();
        TUV tuv = new TUV(getHeader().build().getSrcLang());
        tu.addTUV(tuv);
        for (InlineTag tag : tags) {
            tuv.addContent(tag);
        }
        boolean caughtError = false;
        try {
            writer.writeTu(tu);
        }
        catch (OtterException e) {
            caughtError = true;
        }
        assertEquals("Unexpected result writing tag " + tags, expectFailure, caughtError);
    }
    
    void testRoundtripTUs(List<TU> tus) throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXWriter(w);
        writer.startTMX(getHeader().build());
        for (TU tu : tus) {
            writer.writeTu(tu);
        }
        writer.endTMX();
        w.close();
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXReader reader = TMXReader.createTMXReader(r);
        List<TU> roundtripTUs = readTUs(reader);
        assertEquals(tus.size(), roundtripTUs.size());
        assertEquals(tus, roundtripTUs);
        tmp.delete();
    }

    void verifyAgainstSnippet(String snippetFile, List<TU> tus) throws Exception {
        TMXReader reader = TMXReader.createTMXReader(getSnippetReader(snippetFile));
        List<TU> roundtripTUs = readTUs(reader);
        assertEquals(tus.size(), roundtripTUs.size());
        assertEquals(tus, roundtripTUs);
    }

    void verifyTMX(String snippetFile, List<TU> tus) throws Exception {
        StringWriter sw = new StringWriter();
        TMXWriter writer = TMXWriter.createTMXWriter(sw);
        writer.startTMX(getHeader().build());
        for (TU tu : tus) {
            writer.writeTu(tu);
        }
        writer.endTMX();
        writer.close();
        XMLAssert.assertXMLEqual(getSnippetReader(snippetFile), new StringReader(sw.toString()));
    }

    private Reader getSnippetReader(String snippetResource) {
        return new InputStreamReader(getClass().getResourceAsStream("/snippets/" + snippetResource + ".tmx"),
                                     StandardCharsets.UTF_8);
    }

    @Test
    public void testTu() throws Exception {
        TU tu = new TU("en-US");
        tu.tuvBuilder("en-US")
            .text("Hello ")
            .bpt(1, "<b>")
            .text("paired")
            .ept(1, "</b>")
            .text(" world")
            .build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testTu", Collections.singletonList(tu));
    }
    
    @Test(expected = IllegalStateException.class)
    public void testTUVBuilderMultipleBuildCalls() {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("Hello world");
        b.build();
        b.build(); // This should throw an exception
    }

    @Test
    public void testTuWithSubflow() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b
            .text("Tag containing ")
            .bpt(1, new ComplexContent().addCodes("<a href='#' title='") 
                                        .addSubflow(b.nested().text("Subflow text")) 
                                        .addCodes("'>"))
            .text("a subflow")
            .ept(1, "</a>")
            .text(".")
            .build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testTuWithSubflow", Collections.singletonList(tu));
    }
    
    // Same as previous test, except we call build() on the 
    // nested builder before passing it
    @Test
    public void testTuWithSubflowBuilt() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("Tag containing ")
         .bpt(1, new ComplexContent().addCodes("<a href='#' title='") 
                                     .addSubflow(b.nested().text("Subflow text").build()) 
                                     .addCodes("'>")) // pass mixed content
         .text("a subflow")
         .ept(1, "</a>")
         .text(".")
         .build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testTuWithSubflow", Collections.singletonList(tu));
    }

    @Test
    public void testSimpleTuHighlight() throws Exception {
        TU tu = new TU("en-US");
        tu.tuvBuilder("en-US")
                .text("Content containing ")
                .hi("highlighted")
                .text(" text").build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testSimpleTuHighlight", Collections.singletonList(tu));
    }
    
    @Test
    public void testTuHighlightWithTags() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("Content containing ")
            .hi(b.nested().text("highlighted text including ")
                    .bpt(1, "<b>")
                    .text("tag content")
                    .ept(1, "</b>"))
            .build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testTuHighlightWithTags", Collections.singletonList(tu));
    }
    
    // Same as previous test, except we call build() on the 
    // nested builder before passing it
    @Test
    public void testTuWithHighlightBuilt() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("Content containing ")
            .hi(b.nested().text("highlighted text including ")
                    .bpt(1, "<b>")
                    .text("tag content")
                    .ept(1, "</b>").build())
            .build();
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testTuHighlightWithTags", Collections.singletonList(tu));
        verifyTMX("testTuHighlightWithTags", Collections.singletonList(tu));
    }

    @Test
    public void testDeepHiNesting() throws Exception {
        // <seg>A<hi x="1">B<hi x="2">C</hi>D</hi>.</seg>
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("A");
        TUVBuilder nested = b.nested(); 
        nested.text("B")
              .hi(nested.nested().text("C"))
              .text("D");
        b.hi(nested).text(".");
        tu.addTUV(b.build());
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testDeepHiNesting", Collections.singletonList(tu));
    }


    @Test
    public void testPhAttributes() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("A")
         .tag(new PlaceholderTag("<br/>").setX(1).setType("index").setAssoc("p"))
         .text("B");
        tu.addTUV(b.build());
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testPhAttributes", Collections.singletonList(tu));
    }
    
    @Test
    public void testMultipleTags() throws Exception {
        TU tu = new TU("en-US");
        TUVBuilder b = tu.tuvBuilder("en-US");
        b.text("A")
         .tag(new PlaceholderTag("<br />").setX(1).setType("index"))
         .text("B")
         .tag(new BeginTag(1, new ComplexContent()
                                     .addCodes("<a href='#' title='")
                                     .addSubflow(b.nested().text("this is translatable"))
                                     .addCodes("'>")).setX(2)
                     .setType("link"))
         .text("C")
         .ept(1, "</a>");
        tu.addTUV(b.build());
        testRoundtripTUs(Collections.singletonList(tu));
        verifyAgainstSnippet("testMultipleTags", Collections.singletonList(tu));
    }

    @Test
    public void testUnmatchedPairedTagConversion() throws Exception {
        File tmp = File.createTempFile("otter", ".tmx");
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXWriter(w);
        writer.startTMX(getHeader().build());
        TU tu = new TU();
        TUV src = new TUV("en-US");
        src.addContent(new TextContent("Dangling "));
        src.addContent(new BeginTag(1));
        src.addContent(new TextContent(" tag"));
        tu.addTUV(src);
        writer.writeEvent(new TUEvent(tu));
        writer.endTMX();
        w.close();
        Reader r = new InputStreamReader(new FileInputStream(tmp), "UTF-8");
        TMXReader reader = TMXReader.createTMXReader(r);
        List<TU> tus = readTUs(reader);
        assertEquals(1, tus.size());
        TU tgtTu = tus.get(0);
        Map<String, TUV> tuvs = tgtTu.getTuvs();
        assertEquals(1, tuvs.size());
        TUV tgtTuv = tuvs.get("en-US");
        assertNotNull(tgtTuv);
        List<TUVContent> contents = tgtTuv.getContents();
        assertEquals(3, contents.size());
        assertEquals(new TextContent("Dangling "), contents.get(0));
        IsolatedTag it = new IsolatedTag(IsolatedTag.Pos.BEGIN);
        assertEquals(it, contents.get(1));
        assertEquals(new TextContent(" tag"), contents.get(2));
    }
    
    private HeaderBuilder getHeader() {
        HeaderBuilder header = new HeaderBuilder();
        header.setCreationTool("Otter TMX");
        header.setCreationToolVersion("1.0");
        header.setSegType("sentence");
        header.setTmf("Otter TMX");
        header.setAdminLang("en-US");
        header.setSrcLang("en-US");
        header.setDataType("text");
        header.addProperty(new Property("type1", "Property"));
        header.addNote(new Note("This is a note"));
        return header;
    }
    
    @Test
    public void testRoundtripHeader() throws Exception {
        testRoundtrip("/header.tmx");
    }
    
    @Test
    public void testRoundtripBody() throws Exception {
        testRoundtrip("/body.tmx");
    }
    
    @Test
    public void testRoundtripPairedTags() throws Exception {
        testRoundtrip("/paired_tags.tmx");
    }

    @Test
    public void testRoundtripIsolatedTags() throws Exception {
        testRoundtrip("/it_tag.tmx");
    }
    
    @Test
    public void testHiTags() throws Exception {
        testRoundtrip("/hi_tag.tmx");
    }

    @Test
    public void testNestedHiTags() throws Exception {
        testRoundtrip("/hi_nested.tmx");
    }
    
    @Test
    public void testSubflow() throws Exception {
        testRoundtrip("/subflow.tmx");
    }
    
    public void testRoundtrip(String resourceName) throws Exception {
        InputStream is = getClass().getResourceAsStream(resourceName);
        TMXReader reader = TMXReader.createTMXReader(
                            new InputStreamReader(is, "UTF-8"));
        File tmp = File.createTempFile("otter", ".tmx");
        List<TMXEvent> events = readEvents(reader);
        Writer w = new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8");
        TMXWriter writer = TMXWriter.createTMXWriter(w);
        for (TMXEvent e : events) {
            writer.writeEvent(e);
        }
        w.close();
        
        // Now verify!
        TMXReader roundtripReader = TMXReader.createTMXReader(
                        new InputStreamReader(new FileInputStream(tmp), "UTF-8"));
        List<TMXEvent> roundtripEvents = readEvents(roundtripReader);
        assertEquals(events, roundtripEvents);
        tmp.delete();
    }
    
    // TODO: unittest that verifies that we write the required attributes
    // TODO: properties with encoding and xml:lang properties
    // TODO: notes with encoding and xml:lang properties
}
