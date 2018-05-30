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
package com.medium.rest.boundary;

import com.medium.rest.control.Message;
import com.medium.rest.control.ResourceUriBuilder;
import com.medium.rest.entity.GuestBook;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Fabio Santos Almeida <livre.programacao at gmail.com>
 */
@Stateless
@Path("messages")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class MessageResources {

    @Inject
    Message message;

    @Inject
    ResourceUriBuilder resourceUriBuilder;

    @Context
    UriInfo uriInfo;

    @GET
    public JsonArray findAll() {
        JsonArrayBuilder list = Json.createArrayBuilder();
        List<GuestBook> all = this.message.findAll();
        all.stream().map(m -> m.toJson(
                resourceUriBuilder.createResourceUri(
                        MessageResources.class,
                        "findById",
                        m.getId(),
                        uriInfo
                )
        )).forEach(list::add);
        return list.build();
    }

    @GET
    @Path("{id}")
    public JsonObject findById(@PathParam("id") Long id) {
        GuestBook guestBook = this.message.findById(id);
        return guestBook.toJson(
                resourceUriBuilder.createResourceUri(
                        MessageResources.class,
                        "findById",
                        guestBook.getId(),
                        uriInfo
                )
        );
    }

    @POST
    public Response save(@Valid GuestBook guestBook) {
        this.message.create(guestBook);
        return Response.ok().build();
    }
}
