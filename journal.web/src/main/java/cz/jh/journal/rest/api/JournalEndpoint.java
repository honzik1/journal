/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.jh.journal.rest.api;

import static cz.jh.journal.Const.JOURNAL_API_BASE;
import cz.jh.journal.business.model.Journal;
import cz.jh.journal.business.service.JournalService;
import javax.ws.rs.Path;

/**
 *
 * @author jan.horky
 */
@Path(JOURNAL_API_BASE)
public class JournalEndpoint extends GenericEndpoint<Journal, JournalService> {

}
