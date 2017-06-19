package csdc.tool.fckeditor;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.fckeditor.connector.Connector;
import net.fckeditor.connector.exception.FolderAlreadyExistsException;
import net.fckeditor.connector.exception.InvalidCurrentFolderException;
import net.fckeditor.connector.exception.InvalidNewFolderNameException;
import net.fckeditor.connector.exception.ReadException;
import net.fckeditor.connector.exception.WriteException;
import net.fckeditor.handlers.Command;
import net.fckeditor.handlers.ConnectorHandler;
import net.fckeditor.handlers.PropertiesLoader;
import net.fckeditor.handlers.RequestCycleHandler;
import net.fckeditor.handlers.ResourceType;
import net.fckeditor.requestcycle.Context;
import net.fckeditor.requestcycle.ThreadLocalData;
import net.fckeditor.response.GetResponse;
import net.fckeditor.response.UploadResponse;
import net.fckeditor.tool.Utils;
import net.fckeditor.tool.UtilsFile;
import net.fckeditor.tool.UtilsResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import csdc.tool.SignID;

/**
 * File Browser request dispatcher. This class is the validating and managing
 * instance between the {@link ConnectorServlet connector servlet} and the
 * {@link Connector connector}. It receives the requests, parses the parameters,
 * validates/sanitizes them and mandates them to the connector. After the
 * connector has processed the request, this dispatcher passes the response back
 * to the connector servlet. More over, it intercepts all
 *  from a
 * connector and emits appropriate (localized) messages to the user. The
 * exceptions won't be logged, they simply indicate the connector state.
 * 
 * @version $Id: Dispatcher.java 3764 2009-06-23 16:05:05Z mosipov $
 */
@SuppressWarnings("deprecation")
public class Dispatcher {
	private final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	private Connector connector;

	/**
	 * Initializes this dispatcher. It initializes the connector internally.
	 * Called at connector servlet {@link ConnectorServlet#init()
	 * initialization}.
	 * 
	 * @param servletContext
	 *            reference to the {@link ServletContext} in which the caller is
	 *            running
	 * @throws Exception
	 *             if the dispatcher initialization fails due to some reason
	 */

	Dispatcher(final ServletContext servletContext) throws Exception {
		connector = ConnectorHandler.getConnector();
		connector.init(servletContext);
	}

	/**
	 * Called by the connector servlet to handle a {@code GET} request. In
	 * particular, it handles the {@link Command#GET_FOLDERS GetFolders},
	 * {@link Command#GET_FOLDERS_AND_FILES GetFoldersAndFiles} and
	 * {@link Command#CREATE_FOLDER CreateFolder} commands.
	 * 
	 * @param request
	 *            the current request instance
	 * @return the get response instance associated with this request
	 */

	GetResponse doGet(final HttpServletRequest request) {
		logger.debug("Entering Dispatcher#doGet");
		
		Context context = ThreadLocalData.getContext();
		context.logBaseParameters();
		
		GetResponse getResponse = null;
		// check parameters
		if (!Command.isValidForGet(context.getCommandStr()))
			getResponse = GetResponse.getInvalidCommandError();
		else if (!ResourceType.isValidType(context.getTypeStr()))
			getResponse = GetResponse.getInvalidResourceTypeError();
		else if (!UtilsFile.isValidPath(context.getCurrentFolderStr()))
			getResponse = GetResponse.getInvalidCurrentFolderError();
		else {
			
			// in contrast to doPost the referrer has to send an explicit type
			ResourceType type = context.getResourceType();
			Command command = context.getCommand();
			
			// check permissions for user action
			if ((command.equals(Command.GET_FOLDERS) || command
					.equals(Command.GET_FOLDERS_AND_FILES))
					&& !RequestCycleHandler.isEnabledForFileBrowsing(request))
				getResponse = GetResponse.getGetResourcesDisabledError();
			else if (command.equals(Command.CREATE_FOLDER)
					&& !RequestCycleHandler.isCreateFolderEnabled(request))
				getResponse = GetResponse.getCreateFolderDisabledError();
			else {
				// make the connector calls, catch its exceptions and generate
				// the proper response object
				try {
					if (command.equals(Command.CREATE_FOLDER)) {
						String newFolderNameStr = request
								.getParameter("NewFolderName");
						logger.debug("Parameter NewFolderName: {}",
								newFolderNameStr);				
						String sanitizedNewFolderNameStr = UtilsFile
								.sanitizeFolderName(newFolderNameStr);
						if (Utils.isEmpty(sanitizedNewFolderNameStr))
							getResponse = GetResponse
									.getInvalidNewFolderNameError();
						else {
							logger.debug(
									"Parameter NewFolderName (sanitized): {}",
									sanitizedNewFolderNameStr);
							connector.createFolder(type, context
									.getCurrentFolderStr(),
									sanitizedNewFolderNameStr);
							getResponse = GetResponse.getOK();
						}
					} else if (command.equals(Command.GET_FOLDERS)
							|| command
									.equals(Command.GET_FOLDERS_AND_FILES)) {
						String url = UtilsResponse.getUrl(RequestCycleHandler
								.getUserFilesPath(request), type, context
								.getCurrentFolderStr());
						getResponse = getFoldersAndOrFiles(command, type, context
								.getCurrentFolderStr(), url);
					}
				} catch (InvalidCurrentFolderException e) {
					getResponse = GetResponse.getInvalidCurrentFolderError();
				} catch (InvalidNewFolderNameException e) {
					getResponse = GetResponse.getInvalidNewFolderNameError();
				} catch (FolderAlreadyExistsException e) {
					getResponse = GetResponse.getFolderAlreadyExistsError();
				} catch (WriteException e) {
					getResponse = GetResponse.getCreateFolderWriteError();
				} catch (ReadException e) {
					getResponse = GetResponse.getGetResourcesReadError();
				}
			}
		}
		
		logger.debug("Exiting Dispatcher#doGet");
		return getResponse;
	}
	
	/**
	 * Returns get response for the {@code GetFolders*} commands. This is simply
	 * a helper method.
	 * 
	 * @param command
	 *            the current command, should be only GetFolders or
	 *            GetFoldersAndFiles
	 * @param currentFolder
	 *            the current folder
	 * @param constructedUrl
	 *            the final URL
	 * @return the get response instance associated with this request
	 * @throws InvalidCurrentFolderException
	 *             if the current folder name is invalid or does not exist
	 *             within the underlying backend
	 * @throws ReadException
	 *             if the file attributes and/or folder names could not be read
	 *             due to some reason
	 */
	private GetResponse getFoldersAndOrFiles(final Command command,
			final ResourceType type, final String currentFolder,
			final String constructedUrl) throws InvalidCurrentFolderException,
			ReadException {
		GetResponse getResponse = new GetResponse(command, type,
				currentFolder, constructedUrl);
		getResponse.setFolders(connector.getFolders(type, currentFolder));
		if (command.equals(Command.GET_FOLDERS_AND_FILES))
			getResponse.setFiles(connector.getFiles(type, currentFolder));
		return getResponse;
	}

	/**
	 * Called by the connector servlet to handle a {@code POST} request. In
	 * particular, it handles the {@link Command#FILE_UPLOAD FileUpload} and
	 * {@link Command#QUICK_UPLOAD QuickUpload} commands.
	 * 
	 * @param request
	 *            the current request instance
	 * @return the upload response instance associated with this request
	 */

	@SuppressWarnings("unchecked")
	UploadResponse doPost(final HttpServletRequest request) {
		logger.debug("Entering Dispatcher#doPost");
		Context context = ThreadLocalData.getContext();
		context.logBaseParameters();
		String FilePath = (String) request.getSession().getAttribute("FilePath");
	
		PropertiesLoader.setProperty("connector.userFilesPath", FilePath);
		PropertiesLoader.setProperty("connector.userFilesAbsolutePath", FilePath);
		UploadResponse uploadResponse = null;
		// check permissions for user actions
		if (!RequestCycleHandler.isEnabledForFileUpload(request))
			uploadResponse = UploadResponse.getFileUploadDisabledError();
		// check parameters  
		else if (!Command.isValidForPost(context.getCommandStr()))
			uploadResponse = UploadResponse.getInvalidCommandError();
		else if (!ResourceType.isValidType(context.getTypeStr()))
			uploadResponse = UploadResponse.getInvalidResourceTypeError();
		else if (!UtilsFile.isValidPath(context.getCurrentFolderStr()))
			uploadResponse = UploadResponse.getInvalidCurrentFolderError();
		else {

			// call the Connector#fileUpload
			ResourceType type = context.getDefaultResourceType();
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			try {
				List<FileItem> items = upload.parseRequest(request);
				// We upload just one file at the same time
				FileItem uplFile = items.get(0);
				// Some browsers transfer the entire source path not just the
				// filename
				
				String fileName = FilenameUtils.getName(uplFile.getName());
				String extension = FilenameUtils.getExtension(fileName); 
				
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				String df = format.format(date);
				String signID = SignID.getInstance().getSignID();
				fileName = df + signID +"."+extension;
				
				
				
				logger.debug("Parameter NewFile: {}", fileName);
				// check the extension
				if (type.isNotAllowedExtension(FilenameUtils
						.getExtension(fileName)))
					uploadResponse = UploadResponse.getInvalidFileTypeError();
				// Secure image check (can't be done if QuickUpload)
				else if (type.equals(ResourceType.IMAGE)
						&& PropertiesLoader.isSecureImageUploads()
						&& !UtilsFile.isImage(uplFile.getInputStream())) {
					uploadResponse = UploadResponse.getInvalidFileTypeError();
				} else {
					String sanitizedFileName = UtilsFile
							.sanitizeFileName(fileName);
					logger.debug("Parameter NewFile (sanitized): {}",
							sanitizedFileName);
					String newFileName = connector.fileUpload(type, context
							.getCurrentFolderStr(), sanitizedFileName, uplFile
							.getInputStream());
					String fileUrl = UtilsResponse.fileUrl(RequestCycleHandler
							.getUserFilesPath(request), type, context
							.getCurrentFolderStr(), newFileName);

					if (sanitizedFileName.equals(newFileName))
						uploadResponse = UploadResponse.getOK(fileUrl);
					else {
						uploadResponse = UploadResponse.getFileRenamedWarning(fileUrl, newFileName);
						logger.debug("Parameter NewFile (renamed): {}",
								newFileName);
					}
				}
				
				uplFile.delete();
			} catch (InvalidCurrentFolderException e) {
				uploadResponse = UploadResponse.getInvalidCurrentFolderError();
			} catch (WriteException e) {
				uploadResponse = UploadResponse.getFileUploadWriteError();
			} catch (IOException e) {
				uploadResponse = UploadResponse.getFileUploadWriteError();
			} catch (FileUploadException e) {
				uploadResponse = UploadResponse.getFileUploadWriteError();
			}
		}
		
		logger.debug("Exiting Dispatcher#doPost");
		return uploadResponse;
	}
	
}