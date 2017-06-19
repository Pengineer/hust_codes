package csdc.action.oa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.io.DocumentResult;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.ws.message.StringHeader;

import csdc.bean.Account;
import csdc.bean.DocResult;
import csdc.bean.Document;
import csdc.bean.Person;

import csdc.service.IBaseService;

public class DocumentAction extends ActionSupport {
	
	private IBaseService baseService;
	private Document document;
	private DocResult docResult;
	private String documentId;
	private String personId;
	private int tag;//1:发文
	private Map jsonMap = new HashMap();
	
	public String toList() {
		return SUCCESS;
	}
	
	public String list() {
		ArrayList<Document> documentList = new ArrayList<Document>();
		List<Object[]> aList = new ArrayList<Object[]>();
		try {
			documentList = (ArrayList<Document>) baseService.list(Document.class, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String[] item;
		for (Document doc : documentList) {
			item = new String[8];
			item[0] = doc.getTitle();
			item[1] = doc.getSerialNumber();
			if(doc.getType() == 1) {
				item[2] = "函";
			} else if (doc.getType() == 2) {
				item[2] = "上行公文";
			}  else if (doc.getType() == 3) {
				item[2] = "下行公文";
			}  else if (doc.getType() == 4) {
				item[2] = "平行公文";
			}  else if (doc.getType() == 5) {
				item[2] = "会议纪要";
			}
			
			if(doc.getPriority() == 1) {
				item[3] = "普通";
			} else if (doc.getPriority() == 2) {
				item[3] = "紧急";
			}  else if (doc.getPriority() == 3) {
				item[3] = "非常紧急";
			}
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			item[4] = sdf.format(doc.getDateTime());
			Person person = null;
			if(null != doc.getSender().getBelongId()){
				person = (Person) baseService.load(Person.class, doc.getSender().getBelongId());
				item[5] = person.getRealName();
			} else {
				item[5] = "个人信息暂未完善";
			}
			Map map = new HashMap();
			map.put("documentId", doc.getId());
			DocResult docResult = null;
			try {
				docResult = (DocResult) baseService.list(DocResult.class, map).get(0);
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
			if(docResult.getStatus() == 0) {
				item[6] = "暂存";
			} else if (doc.getPriority() == 1) {
				item[6] = "审批中";
			}  else if (doc.getPriority() == 2) {
				item[6] = "完成";
			}
			item[7] = doc.getId();
			aList.add(item);
		}
		jsonMap.put("aaData", aList);
		return SUCCESS;
	}
	
	public String toAdd() {
		return SUCCESS;
	}
	
	public String add() {
		Map session = ActionContext.getContext().getSession();
		Account sender = (Account) session.get("account");
		document.setSender(sender);
		Person person = (Person) baseService.load(Person.class, personId);
		Map map = new HashMap();
		map.put("belongId", person.getId());
		Account reciever = new Account();
		try {
			reciever = (Account) baseService.list(Account.class, map).get(0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		document.setReciever(reciever);
		document.setDateTime(new Date());
		try {
			baseService.add(document);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DocResult docResult = new DocResult();
		docResult.setDocument(document);
		docResult.setAuditor(reciever);
		docResult.setStatus(1);
		try {
			baseService.add(docResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String toModify() {
		document = (Document) baseService.load(Document.class, documentId);
		return SUCCESS;
	}
	
	public String modify() {
		Document doc = new Document();
		doc.setLevel(document.getLevel());
		doc.setPriority(document.getPriority());
		doc.setSerialNumber(document.getSerialNumber());
		doc.setSubject(document.getSubject());
		doc.setTitle(document.getTitle());
		doc.setType(document.getType());
		doc.setId(document.getId());
		try {
			baseService.modify(doc);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String delete() {
		Map map = new HashMap();
		map.put("documentId", documentId);
		List<DocResult> docResults = baseService.list(DocResult.class, map);
		for (int i = 0; i < docResults.size(); i++) {
			baseService.delete(DocResult.class, docResults.get(i).getId());
		}
		baseService.delete(Document.class, documentId);
		return SUCCESS;
	}
	
	public String toAudit() {
		document = (Document) baseService.load(Document.class, documentId);
		return SUCCESS;
	}
	
	public String audit() {
		DocResult docRe = new DocResult();
		docRe.setOpinion(docResult.getOpinion());
		docRe.setStatus(docResult.getStatus());
		docRe.setDateTime(new Date());
		Map session = ActionContext.getContext().getSession();
		Account account = (Account) session.get("account");
		docRe.setAuditor(account);
		
		
		docRe.setDocument(document);
		baseService.add(docRe);
		return SUCCESS;
	}
	
	public String view() {
		
		try {
			document = (Document) baseService.load(Document.class, documentId);
			Map map = new HashMap();
			map.put("documentId", documentId);
			List<DocResult> docResults = baseService.list(DocResult.class, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public IBaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}



	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public int getTag() {
		return tag;
	}

	public void setTag(int tag) {
		this.tag = tag;
	}

	public Map getJsonMap() {
		return jsonMap;
	}

	public void setJsonMap(Map jsonMap) {
		this.jsonMap = jsonMap;
	}

	public DocResult getDocResult() {
		return docResult;
	}

	public void setDocResult(DocResult docResult) {
		this.docResult = docResult;
	}

}