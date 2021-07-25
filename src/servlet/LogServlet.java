package servlet;

import pojo.Imp.LogRecordImp;

import javax.servlet.http.HttpServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/log")
public class LogServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LogRecordImp logRecordImp=new LogRecordImp();
        req.setAttribute("list",logRecordImp.findAllLogRecord());
        req.getRequestDispatcher("log.jsp").forward(req,resp);
    }
}
