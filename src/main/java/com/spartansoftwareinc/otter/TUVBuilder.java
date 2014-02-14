package com.spartansoftwareinc.otter;

/**
 * Convenience class to simplify the creation 
 * of mixed content TUs.
 */
public class TUVBuilder {
	private TU tu = null;
	private TUV tuv;
	private boolean built = false;
	
	TUVBuilder(String locale) {
		tuv = new TUV(locale);
	}

	TUVBuilder(TU tu, String locale) {
	    this(locale);
	    this.tu = tu;
	}
	
	/**
	 * Return a nested TUVBuilder that will insert its
	 * contents at the current build point.
	 * @return a new TUVBuilder inserted at the current point
	 *         in the parent TUV
	 */
	public TUVBuilder nested() {
	    return new TUVBuilder(tuv.getLocale());
	}
	
	/**
	 * Generate a <code>TUV</code> based on the content that has been
	 * added to this <code>TUVBuilder</code>.  This <code>TUV<code> will also
	 * be added to the {@link TU} that created this object.
	 * <p>   
	 * Calling this method multiple times will cause an 
	 * {@link IllegalStateException} to be thrown.
	 * @return TUV constructed from the contents of this builder
	 */
	public TUV build() {
	    if (built) {
	        throw new IllegalStateException(
	                "TUVBuilder.built() called multiple times");
	    }
	    built = true;
	    if (tu != null) {
	        tu.addTUV(tuv);
	    }
		return tuv;
	}
	
	/**
	 * Add text to thi
	 * @param content
	 * @return this TUVBuilder
	 */
	public TUVBuilder text(String content) {
		tuv.addContent(new TextContent(content));
		return this;
	}
	
	/**
	 * Add {@link TagContent} to this TUV.  Not all 
	 * {@link TagContent} objects may be added at all times - 
	 * the builder will enforce the same restrictions as various
	 * implementations of {@link TUVContentSink#addContent(TUVContent)},
	 * as appropriate.
	 * @param content {@link TUVContent} object to add.
	 * @return this TUVBuilder
	 */
	public TUVBuilder tag(TagContent content) {
	    tuv.addContent(content);
	    return this;
	}
	
	/**
	 * Add a {@link BeginTag} with the specified <code>i</code> value and
	 * code content.
	 * @param i
	 * @param codeContent
	 * @return this TUVBuilder
	 */
	public TUVBuilder bpt(int i, String codeContent) {
		BeginTag bpt = new BeginTag(i);
		return addCodeContent(bpt, codeContent);
	}
	
    /**
     * Add a {@link BeginTag} with the specified <code>i</code> and 
     * <code>x</code> value and code content.
     * @param i
     * @param codeContent
     * @return this TUVBuilder
     */
	public TUVBuilder bpt(int i, int x, String codeContent) {
	    BeginTag bpt = new BeginTag(i);
	    bpt.setX(x);
        return addCodeContent(bpt, codeContent);
	}
	
    /**
     * Add a {@link BeginTag} with the specified <code>i</code> value and
     * complex code contents.
     * @param i
     * @param content
     * @return this TUVBuilder
     */
	public TUVBuilder bpt(int i, ComplexContent content) {
	    BeginTag bpt = new BeginTag(i);
	    return addComplexContent(bpt, content);
	}
	
    /**
     * Add a {@link BeginTag} with the specified <code>i</code> and
     * <code>x</code> values and complex code contents.
     * @param i
     * @param content
     * @return this TUVBuilder
     */
	public TUVBuilder bpt(int i, int x, ComplexContent content) {
	    BeginTag bpt = new BeginTag(i);
        bpt.setX(x);    
        return addComplexContent(bpt, content);
    }
	
	/**
	 * Add a {@link EndTag} with the specified <code>i</code> value
	 * and code contents.
	 * @param i
	 * @param codeContent
	 * @return this TUVBuilder
	 */
	public TUVBuilder ept(int i, String codeContent) {
		tuv.addContent(new EndTag(i, codeContent));
		return this;	
	}
	
	/**
	 * Add a {@link PlaceholderTag} with the specified code contents.
	 * @param codeContent
	 * @return this TUVBuilder
	 */
	public TUVBuilder ph(String codeContent) {
		PlaceholderTag ph = new PlaceholderTag();
		return addCodeContent(ph, codeContent);
	}

	/**
	 * Add a {@link PlaceholderTag} with the specified <code>x</code>
	 * value and code contents.
	 * @param codeContent
	 * @return this TUVBuilder
	 */
	public TUVBuilder ph(int x, String codeContent) {
        PlaceholderTag ph = new PlaceholderTag();
        ph.setX(x);
        return addCodeContent(ph, codeContent);
    }
	
	/**
	 * Add a {@link PlaceholderTag} with the specified complex content.
	 * @param content
	 * @return this TUVBuilder
	 */
	public TUVBuilder ph(ComplexContent content) {
	    PlaceholderTag ph = new PlaceholderTag();
	    return addComplexContent(ph, content);
	}
	
   /**
     * Add a {@link PlaceholderTag} with the specified <code>x</code>
     * value and complex code contents.
     * @param content
     * @return this TUVBuilder
     */
    public TUVBuilder ph(int x, ComplexContent content) {
        PlaceholderTag ph = new PlaceholderTag();
        ph.setX(x);
        return addComplexContent(ph, content);
    }
	
    /**
     * Add a {@link IsolatedTag} with the specified <code>pos</code>
     * value and code contents.
     * @param pos
     * @param codeContent
     * @return this TUVBuilder
     */
    public TUVBuilder it(IsolatedTag.Pos pos, String codeContent) {
        IsolatedTag it = new IsolatedTag(pos);
        return addCodeContent(it, codeContent);
    }
    
    /**
     * Add a {@link IsolatedTag} with the specified <code>pos</code>
     * value and complex contents.
     * @param pos
     * @param content
     * @return this TUVBuilder
     */
    public TUVBuilder it(IsolatedTag.Pos pos, ComplexContent content) {
        IsolatedTag it = new IsolatedTag(pos);
        return addComplexContent(it, content);
    }
	
    /**
     * Add a {@link HighlightTag} with the specified text content.
     * @param textContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(String textContent) {
        HighlightTag hi = new HighlightTag();
        hi.addContent(new TextContent(textContent));
        tuv.addContent(hi);
        return this;
    }
    
    /**
     * Add a {@link HighlightTag} with the specified <code>x</code> value
     * and text content.
     * @param x
     * @param textContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(int x, String textContent) {
        HighlightTag hi = new HighlightTag().setX(x);
        hi.addContent(new TextContent(textContent));
        tuv.addContent(hi);
        return this;
    }
    
    /**
     * Add a {@link HighlightTag} with the content collected in the specified
     * {@link TUVBuilder}.
     * and text content.
     * @param highlightedContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(TUVBuilder highlightedContent) {
        return hi(highlightedContent.build());
    }
    
    /**
     * Add a {@link HighlightTag} with the specified <code>x</code> value and
     * the content collected in the specified {@link TUVBuilder}.
     * @param x
     * @param highlightedContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(int x, TUVBuilder highlightedContent) {
        return hi(x, highlightedContent.build());
    }
    
    /**
     * Add a {@link HighlightTag} with the content collected in the specified
     * {@link TUV}.
     * and text content.
     * @param highlightedContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(TUV highlightedContent) {
        HighlightTag hi = new HighlightTag();
        hi.addContents(highlightedContent.getContents());
        tuv.addContent(hi);
        return this;
    }
    
    /**
     * Add a {@link HighlightTag} with the specified <code>x</code> value and
     * the content collected in the specified {@link TUV}.
     * @param x
     * @param highlightedContent
     * @return this TUVBuilder
     */
    public TUVBuilder hi(int x, TUV highlightedContent) {
        HighlightTag hi = new HighlightTag().setX(x);
        hi.addContents(highlightedContent.getContents());
        tuv.addContent(hi);
        return this;
    }
	
	protected TUVBuilder addCodeContent(TagContent sink, String codes) {
	    sink.addContent(new CodeContent(codes));
	    tuv.addContent(sink);
	    return this;
	}
	
	protected TUVBuilder addComplexContent(TagContent sink, ComplexContent content) {
	    sink.addContents(content.getContent());
	    tuv.addContent(sink);
	    return this;
	}
}
