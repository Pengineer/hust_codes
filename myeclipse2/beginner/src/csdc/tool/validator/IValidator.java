package csdc.tool.validator;

/**
 * 通用校验接口
 * @author 徐涵
 *
 */
public interface IValidator {
	StringBuffer validate(Object obj) throws Exception;
}
