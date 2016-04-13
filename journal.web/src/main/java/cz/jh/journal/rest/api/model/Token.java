/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jan.horky
 */
@XmlRootElement
public class Token {

    private String payload;

    public Token() {
    }

    public Token(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

}
