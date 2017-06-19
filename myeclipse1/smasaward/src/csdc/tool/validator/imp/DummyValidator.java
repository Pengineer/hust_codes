package csdc.tool.validator.imp;

import csdc.tool.validator.IValidator;

/**
 * 哑校验器，无条件返回"正确"
 * @author Administrator
 *
 */
public class DummyValidator implements IValidator{
	public StringBuffer validate(Object obj) throws Exception {
		StringBuffer ret = new StringBuffer("");
		return ret;
	}

}
