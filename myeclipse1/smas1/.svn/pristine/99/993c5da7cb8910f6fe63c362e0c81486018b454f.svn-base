package csdc.tool.execution.importer;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;

import csdc.dao.HibernateBaseDao;
import csdc.tool.execution.Execution;
import csdc.tool.reader.TableDataReader;

public abstract class Importer extends Execution {
	
	public String[] row;
	
	public String A;
	public String B;
	public String C;
	public String D;
	public String E;
	public String F;
	public String G;
	public String H;
	public String I;
	public String J;
	public String K;
	public String L;
	public String M;
	public String N;
	public String O;
	public String P;
	public String Q;
	public String R;
	public String S;
	public String T;
	public String U;
	public String V;
	public String W;
	public String X;
	public String Y;
	public String Z;
	public String AA;
	public String AB;
	public String AC;
	public String AD;
	public String AE;
	public String AF;
	public String AG;
	public String AH;
	public String AI;
	public String AJ;
	public String AK;
	public String AL;
	public String AM;
	public String AN;
	public String AO;
	public String AP;
	public String AQ;
	public String AR;
	public String AS;
	public String AT;
	public String AU;
	public String AV;
	public String AW;
	public String AX;
	public String AY;
	public String AZ;
	public String BA;
	public String BB;
	public String BC;
	public String BD;
	public String BE;
	public String BF;
	public String BG;
	public String BH;
	public String BI;
	public String BJ;
	public String BK;
	public String BL;
	public String BM;
	public String BN;
	public String BO;
	public String BP;
	public String BQ;
	
	@Autowired
	protected HibernateBaseDao dao;

	/**
	 * 当前已写入的条目数
	 */
	public int sessionRecords;
	
	/**
	 * 每写入多少条目数，就flush并clear一次
	 */
	public final int SESSION_RECORDS_MAX = 20000;
	
	///////////////////////////////////////////////////////////////

	
	/**
	 * 读取下一行
	 * 将下一行的数据放进row 和 A B C D E……
	 * @return 是否成功读取下一行
	 * @throws Exception 
	 * @throws UnsupportedEncodingException 
	 */
	public boolean next(TableDataReader reader) throws Exception {
		if (!reader.hasNext()) {
			return false;
		}
		row = reader.next();
		getRowData();
		return true;
	}

	/**
	 * 将一行数据放入预设的变量，便于使用
	 * @param row
	 */
	public void getRowData() {
		A = row.length > 0 ? row[0] : null;
		B = row.length > 1 ? row[1] : null;
		C = row.length > 2 ? row[2] : null;
		D = row.length > 3 ? row[3] : null;
		E = row.length > 4 ? row[4] : null;
		F = row.length > 5 ? row[5] : null;
		G = row.length > 6 ? row[6] : null;
		H = row.length > 7 ? row[7] : null;
		I = row.length > 8 ? row[8] : null;
		J = row.length > 9 ? row[9] : null;
		K = row.length > 10 ? row[10] : null;
		L = row.length > 11 ? row[11] : null;
		M = row.length > 12 ? row[12] : null;
		N = row.length > 13 ? row[13] : null;
		O = row.length > 14 ? row[14] : null;
		P = row.length > 15 ? row[15] : null;
		Q = row.length > 16 ? row[16] : null;
		R = row.length > 17 ? row[17] : null;
		S = row.length > 18 ? row[18] : null;
		T = row.length > 19 ? row[19] : null;
		U = row.length > 20 ? row[20] : null;
		V = row.length > 21 ? row[21] : null;
		W = row.length > 22 ? row[22] : null;
		X = row.length > 23 ? row[23] : null;
		Y = row.length > 24 ? row[24] : null;
		Z = row.length > 25 ? row[25] : null;
		AA = row.length > 26 ? row[26] : null;
		AB = row.length > 27 ? row[27] : null;
		AC = row.length > 28 ? row[28] : null;
		AD = row.length > 29 ? row[29] : null;
		AE = row.length > 30 ? row[30] : null;
		AF = row.length > 31 ? row[31] : null;
		AG = row.length > 32 ? row[32] : null;
		AH = row.length > 33 ? row[33] : null;
		AI = row.length > 34 ? row[34] : null;
		AJ = row.length > 35 ? row[35] : null;
		AK = row.length > 36 ? row[36] : null;
		AL = row.length > 37 ? row[37] : null;
		AM = row.length > 38 ? row[38] : null;
		AN = row.length > 39 ? row[39] : null;
		AO = row.length > 40 ? row[40] : null;
		AP = row.length > 41 ? row[41] : null;
		AQ = row.length > 42 ? row[42] : null;
		AR = row.length > 43 ? row[43] : null;
		AS = row.length > 44 ? row[44] : null;
		AT = row.length > 45 ? row[45] : null;
		AU = row.length > 46 ? row[46] : null;
		AV = row.length > 47 ? row[47] : null;
		AW = row.length > 48 ? row[48] : null;
		AX = row.length > 49 ? row[49] : null;
		AY = row.length > 50 ? row[50] : null;
		AZ = row.length > 51 ? row[51] : null;
		BA = row.length > 52 ? row[52] : null;
		BB = row.length > 53 ? row[53] : null;
		BC = row.length > 54 ? row[54] : null;
		BD = row.length > 55 ? row[55] : null;
		BE = row.length > 56 ? row[56] : null;
		BF = row.length > 57 ? row[57] : null;
		BG = row.length > 58 ? row[58] : null;
		BH = row.length > 59 ? row[59] : null;
		BI = row.length > 60 ? row[60] : null;
		BJ = row.length > 61 ? row[61] : null;
		BK = row.length > 62 ? row[62] : null;
		BL = row.length > 63 ? row[63] : null;
		BM = row.length > 64 ? row[64] : null;
		BN = row.length > 65 ? row[65] : null;
		BO = row.length > 66 ? row[66] : null;
		BP = row.length > 67 ? row[67] : null;
		BQ = row.length > 68 ? row[68] : null;
	}
	
	//////////////////////////////////////////////////////////////////////
	
	protected void saveOrUpdate(Object entity) {
		saveOrUpdate(entity, false);
	}
	
	/**
	 * 在当前session内添加一条写入
	 * 若添加条数达到SESSION_RECORDS_MAX，则flush并清空session
	 * @param entity
	 * @param suppressClear 为true则强制不flush&clear
	 */
	protected void saveOrUpdate(Object entity, boolean suppressClear) {
		dao.addOrModify(entity);

		if (++sessionRecords > SESSION_RECORDS_MAX && !suppressClear) {
			dao.flush();
			dao.clear();
			sessionRecords = 0;
			System.out.println("sessionRecords: " + sessionRecords);
		}
	}
}
