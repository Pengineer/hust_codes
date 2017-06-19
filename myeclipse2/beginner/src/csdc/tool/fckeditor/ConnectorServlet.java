package csdc.tool.fckeditor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.fckeditor.requestcycle.ThreadLocalData;
import net.fckeditor.response.GetResponse;
import net.fckeditor.response.UploadResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FCK上传文件时的文件名编码在处理中文会乱码，重写该函数将编码改为UTF-8
 * @author 雷达
 *
 */
public class ConnectorServlet extends HttpServlet {
	private static final long serialVersionUID = -5742008970929377161L;
	private final Logger logger = LoggerFactory.getLogger(ConnectorServlet.class);
	private transient Dispatcher dispatcher;

	@Override
	public void init() throws ServletException {
		try {
			dispatcher = new Dispatcher(getServletContext());
		} catch (Exception e) {
			logger.error("Dispatcher could not be initialized", e);
			throw new ServletException(e);
		}
	}

	/**
	 * Passes a GET request to the dispatcher.
	 * 
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the GET request
	 * @throws ServletException
	 *             if the request for the GET could not be handled
	 */
	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/xml");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		GetResponse getResponse = null;

		try {
			ThreadLocalData.beginRequest(request);
			getResponse = dispatcher.doGet(request);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			/*
			 * call this method to prevent detached requests or else the request
			 * will probably never be garbage collected and will fill your
			 * memory
			 */
			ThreadLocalData.endRequest();
		}

		out.print(getResponse);
		out.flush();
		out.close();
	}

	/**
	 * Passes a POST request to the dispatcher.
	 * 
	 * @throws IOException
	 *             if an input or output error is detected when the servlet
	 *             handles the request
	 * @throws ServletException
	 *             if the request for the POST could not be handled
	 */
	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		UploadResponse uploadResponse = null;

		try {
			ThreadLocalData.beginRequest(request);
			uploadResponse = dispatcher.doPost(request);
		} catch (Exception e) {
			throw new ServletException(e);
		} finally {
			/*
			 * call this method to prevent detached requests or else the request
			 * will probably never be garbage collected and will fill your
			 * memory
			 */
			ThreadLocalData.endRequest();
		}

		out.print(uploadResponse);
		out.flush();
		out.close();
	}

}
