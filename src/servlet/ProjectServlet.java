package servlet;
import service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ProjectServlet extends HttpServlet {
    public ProjectServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置请求的编码格式
        request.setCharacterEncoding("UTF-8");
        // 设置响应的编码格式
        response.setContentType("application/json; charset=utf-8");
        String servlerpath=request.getServletPath();
        System.out.println(servlerpath);
        int index=servlerpath.lastIndexOf('/');
        String methodname=servlerpath.substring(index+1,servlerpath.length()-15);
        System.out.println(methodname);
        Method method=null;
        ProjectService projectService = new ProjectService();
        Class clz=ProjectService.class;
        try {
            try {
                method=clz.getDeclaredMethod(methodname, HttpServletRequest.class,HttpServletResponse.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            System.out.println(method);
            method.invoke(projectService,request,response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
