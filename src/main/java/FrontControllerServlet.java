package frame;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class FrontControllerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        processRequest(req, resp);
    }

    protected void processRequest(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String uri     = req.getRequestURI();
        String method  = req.getMethod();
        String appName = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='fr'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>Mini-Framework MVC</title>");
        out.println("  <style>");
        out.println("    * { box-sizing: border-box; margin: 0; padding: 0; }");
        out.println("    body { font-family: 'Segoe UI', sans-serif; background: #0f172a; color: #e2e8f0; min-height: 100vh; display: flex; align-items: center; justify-content: center; }");
        out.println("    .card { background: #1e293b; border: 1px solid #334155; border-radius: 12px; padding: 40px 50px; max-width: 680px; width: 90%; box-shadow: 0 20px 60px rgba(0,0,0,0.4); }");
        out.println("    .badge { display: inline-block; background: #6366f1; color: white; font-size: 11px; font-weight: 700; padding: 3px 10px; border-radius: 20px; letter-spacing: 1px; text-transform: uppercase; margin-bottom: 18px; }");
        out.println("    h1 { font-size: 28px; font-weight: 700; color: #f8fafc; margin-bottom: 8px; }");
        out.println("    .subtitle { color: #94a3b8; font-size: 15px; margin-bottom: 30px; }");
        out.println("    .info-grid { display: grid; gap: 12px; }");
        out.println("    .info-row { background: #0f172a; border-radius: 8px; padding: 14px 18px; display: flex; justify-content: space-between; align-items: center; border-left: 3px solid #6366f1; }");
        out.println("    .info-label { color: #64748b; font-size: 13px; text-transform: uppercase; letter-spacing: 0.5px; }");
        out.println("    .info-value { color: #a5b4fc; font-weight: 600; font-size: 14px; word-break: break-all; text-align: right; max-width: 60%; }");
        out.println("    .footer { margin-top: 28px; padding-top: 20px; border-top: 1px solid #334155; text-align: center; color: #475569; font-size: 13px; }");
        out.println("    .dot { display: inline-block; width: 8px; height: 8px; background: #22c55e; border-radius: 50%; margin-right: 6px; animation: pulse 2s infinite; }");
        out.println("    @keyframes pulse { 0%,100%{opacity:1} 50%{opacity:0.4} }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");
        out.println("  <div class='card'>");
        out.println("    <div class='badge'>&#9881; Mini-Framework</div>");
        out.println("    <h1>FrontControllerServlet</h1>");
        out.println("    <p class='subtitle'>Toutes les requêtes convergent ici &mdash; depuis le Framework (JAR)</p>");
        out.println("    <div class='info-grid'>");
        out.println("      <div class='info-row'><span class='info-label'>URL complète</span><span class='info-value'>" + uri + "</span></div>");
        out.println("      <div class='info-row'><span class='info-label'>Méthode HTTP</span><span class='info-value'>" + method + "</span></div>");
        out.println("      <div class='info-row'><span class='info-label'>Application</span><span class='info-value'>" + (appName.isEmpty() ? "/" : appName) + "</span></div>");
        out.println("      <div class='info-row'><span class='info-label'>Servlet Class</span><span class='info-value'>mg.framework.FrontControllerServlet</span></div>");
        out.println("      <div class='info-row'><span class='info-label'>Source</span><span class='info-value'>mini-framework.jar</span></div>");
        out.println("    </div>");
        out.println("    <div class='footer'><span class='dot'></span>Framework actif &mdash; processRequest() en cours d&apos;ex&eacute;cution</div>");
        out.println("  </div>");
        out.println("</body>");
        out.println("</html>");
    }
}