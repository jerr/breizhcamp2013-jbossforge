package org.printstacktrace.logbook.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import org.printstacktrace.logbook.model.Application;

/**
 * 
 */
@Stateless
@Path("/applications")
public class ApplicationEndpoint
{
   @PersistenceContext(unitName = "forge-default")
   private EntityManager em;

   @POST
   @Consumes("application/json")
   public Response create(Application entity)
   {
      em.persist(entity);
      return Response.created(UriBuilder.fromResource(ApplicationEndpoint.class).path(String.valueOf(entity.getId())).build()).build();
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") Long id)
   {
      Application entity = em.find(Application.class, id);
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      em.remove(entity);
      return Response.noContent().build();
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces("application/json")
   public Response findById(@PathParam("id") Long id)
   {
      TypedQuery<Application> findByIdQuery = em.createQuery("SELECT a FROM Application a LEFT JOIN FETCH a.servers WHERE a.id = :entityId", Application.class);
      findByIdQuery.setParameter("entityId", id);
      Application entity = findByIdQuery.getSingleResult();
      if (entity == null)
      {
         return Response.status(Status.NOT_FOUND).build();
      }
      return Response.ok(entity).build();
   }

   @GET
   @Produces("application/json")
   public List<Application> listAll()
   {
      final List<Application> results = em.createQuery("SELECT a FROM Application a LEFT JOIN FETCH a.servers", Application.class).getResultList();
      return results;
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Consumes("application/json")
   public Response update(@PathParam("id") Long id, Application entity)
   {
      entity.setId(id);
      entity = em.merge(entity);
      return Response.noContent().build();
   }
}