package com.rev_connect_api.models;
import static org.assertj.core.api.Assertions.assertThatException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class EndorsementLinkModelTest { 
    
    @Test
	void goodLinkCreation() {
        EndorsementLink goodLink = new EndorsementLink((long)1, "https://www.google.com", "this is a good link");
        assertEquals((long)1, goodLink.getUser());
        assertEquals("https://www.google.com", goodLink.getLink());
        assertEquals("this is a good link", goodLink.getLinkText());
	}

    @Test
    void badLinkCreation(){
        new EndorsementLink((long)2, "badwebsite.com", "this is a bad link");
        assertThatException();
    }
}
