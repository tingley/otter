package com.spartansoftwareinc.otter;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper for complex tag contents for various tag classes including
 * {@link BeginTag}, {@link PlaceholderTag}, and {@link IsolatedTag}.  
 * <p>
 * Complex tag contents can include code data or embedded subflows.
 */
public class ComplexContent {
    private List<TUVContent> content = new ArrayList<TUVContent>();
    
    public ComplexContent() {
    }
    
    /**
     * Add code content.
     */
    public ComplexContent addCodes(String codeContent) {
        content.add(new CodeContent(codeContent));
        return this;
    }
    
    /**
     * Add the contents of a {@link TUVBuilder} as a subflow
     * @param tuvContent
     */
    public ComplexContent addSubflow(TUVBuilder builder) {
        content.add(convertToSubflow(builder.build()));
        return this;
    }

    /**
     * Add the contents of a {@link TUV} as a subflow
     * @param tuvContent
     */
    public ComplexContent addSubflow(TUV tuv) {
        content.add(convertToSubflow(tuv));
        return this;
    }
    
    /**
     * Get the tag contents represented by this object.
     * @return list of {@link TUVContent}
     */
    public List<TUVContent> getContent() {
        return content;
    }
    
    private Subflow convertToSubflow(TUV tuv) {
        Subflow subflow = new Subflow();
        subflow.addContents(tuv.getContents());
        return subflow;
    }

}
