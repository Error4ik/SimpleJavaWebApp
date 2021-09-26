package org.example.servlets;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class CRUDServletTest {

    private final CRUDServlet crudServlet = new CRUDServlet();
    private final HttpServletRequest request = mock(HttpServletRequest.class);
    private final HttpServletResponse response = mock(HttpServletResponse.class);
    private final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    private final String path = "/WEB-INF/views/index.jsp";
    private final String redirectPath = String.format("%s/", request.getContextPath());
    private final UUID uuid = UUID.randomUUID();

    @Before
    public void init() throws Exception {
        crudServlet.init();
    }

    @Test
    public void serviceDoGet() throws Exception {
        when(request.getMethod()).thenReturn("GET");
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);

        crudServlet.service(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher, times(1)).forward(request, response);
    }

    @Test
    public void serviceDoPost() throws Exception {
        when(request.getParameter("id")).thenReturn("");
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("surname")).thenReturn("surname");
        when(request.getParameter("age")).thenReturn("25");
        when(request.getParameter("email")).thenReturn("email");

        crudServlet.service(request, response);

        verify(request, times(6)).getParameter(anyString());
        verify(response).sendRedirect(redirectPath);
    }

    @Test
    public void serviceDoPut() throws Exception {
        when(request.getParameter("id")).thenReturn(uuid.toString());
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("type")).thenReturn(null);
        when(request.getParameter("name")).thenReturn("name");
        when(request.getParameter("surname")).thenReturn("surname");
        when(request.getParameter("age")).thenReturn("25");
        when(request.getParameter("email")).thenReturn("email");

        crudServlet.service(request, response);

        verify(request, times(7)).getParameter(anyString());
        verify(response).sendRedirect(redirectPath);
    }

    @Test
    public void serviceDoDelete() throws Exception {
        when(request.getParameter("id")).thenReturn(uuid.toString());
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("type")).thenReturn("DELETE");

        crudServlet.service(request, response);

        verify(request, times(3)).getParameter(anyString());
    }

//    @Test
//    public void testWithRestAssured() throws Exception {
//        Response response = RestAssured.get("http://localhost:8085/crud-servlet/");
//
//        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
//    }
}
