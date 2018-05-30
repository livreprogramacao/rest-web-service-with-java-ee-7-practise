/*
 * Copyright 2018 Fabio Santos Almeida <livre.programacao at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.medium.rest.control;

import com.medium.rest.entity.GuestBook;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Fabio Santos Almeida <livre.programacao at gmail.com>
 */
@Stateless
public class Message {

    @PersistenceContext(name = "prod")
    EntityManager em;

    public List<GuestBook> findAll() {
        return this.em.createNamedQuery(GuestBook.FIND_ALL).getResultList();
    }

    public GuestBook findById(Long id) {
        return this.em.find(GuestBook.class, id);
    }

    public void create(GuestBook guestBook) {
        this.em.persist(guestBook);
    }

    public void remove(GuestBook guestBook) {
        this.em.remove(guestBook);
    }
}
