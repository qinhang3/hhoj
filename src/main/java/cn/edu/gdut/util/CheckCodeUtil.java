package cn.edu.gdut.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

/**
 * 验证码工具类
 * 
 * @author qinhang.qh
 *
 */
public class CheckCodeUtil {

	private String CODEKEY;// 放到session中的key
	private Random random = new Random();
	private String randString = "23456789ABCDEFGHJKMNPQRSTUVWXYZabcdefghjkmnpqrstuvwxyz";// 随机产生的字符串
	// private String randString = "0oO";

	private int checkCodeLength;// 随机产生字符数量
	private int width;// 图片宽
	private int height = 50;// 图片高
	private int lineSize;// 干扰线数量

	/**
	 * 判断验证码输入是否正确<br>
	 * 由于每次验证码答案在请求验证码时才重置<br>
	 * 建议每次检测完成后从session删除正确答案<br>
	 * 防止在绕过请求验证码的情况下被多次请求利用
	 * 
	 * @param codeKey
	 * @param request
	 * @param delete
	 *            默认<code>true</code> 若<b>确认</b>不删除无安全风险可置为false
	 * @return
	 */
	public static boolean checkCode(String codeKey, HttpServletRequest request, boolean delete) {
		CheckCodeUtil checkCodeUtil = new CheckCodeUtil(codeKey,0);
		return checkCodeUtil.checkCode(request, delete);
	}

	/**
	 * 获取验证码图片<br>
	 * 返回正确答案<br>
	 * 会自动写入session
	 * 
	 * @param codeKey
	 * @param request
	 * @param response
	 * @return
	 */

	public static String getPic(String codeKey, HttpServletRequest request, HttpServletResponse response,int checkCodeLength) {
		CheckCodeUtil checkCodeUtil = new CheckCodeUtil(codeKey,checkCodeLength);
		return checkCodeUtil.getRandomCheckPic(request, response);
	}

	private CheckCodeUtil(String codeKey, int checkCodeLength) {
		this.CODEKEY = OJConstant.SessionKey.CHECKCODEKEY+"-"+codeKey;
		this.checkCodeLength = checkCodeLength;
		this.width = checkCodeLength * 40;
		this.lineSize = checkCodeLength * 20;
	}

	/**
	 * 生成随机图片
	 */
	private String getRandomCheckPic(HttpServletRequest request, HttpServletResponse response) {
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		String randomString = getRandomString();
		drawPicByString(request, response, randomString);
		return randomString;
	}

	private void drawPicByString(HttpServletRequest request, HttpServletResponse response, String randomString) {
		BufferedImage image = drawPic(randomString);

		HttpSession session = request.getSession();
		session.removeAttribute(CODEKEY);
		session.setAttribute(CODEKEY, randomString.toUpperCase());

		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkCode(HttpServletRequest request, boolean delete) {
		if (StringUtils.isEmpty(request.getParameter("code")))
			return false;
		if (request.getSession().getAttribute(CODEKEY) == null)
			return false;
		if (StringUtils.isEmpty(request.getSession().getAttribute(CODEKEY).toString()))
			return false;
		String code = request.getParameter("code").toUpperCase();
		String stdCode = request.getSession().getAttribute(CODEKEY).toString();
		return checkCode(request, delete, code, stdCode);
	}

	private boolean checkCode(HttpServletRequest request, boolean delete, String code, String stdCode) {
		if (StringUtils.equals(code, stdCode)) {
			if (delete)
				request.getSession().setAttribute(CODEKEY, null);
			return true;
		} else {
			request.getSession().setAttribute(CODEKEY, null);
			return false;
		}
	}

	private BufferedImage drawPic(String randomString) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(getFont());
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drowLine(g);
		}
		// 绘制字符
		for (int i = 0; i < checkCodeLength; i++) {
			randomString = drowString(g, randomString, i + 1, randomString.charAt(i));
		}

		g.dispose();
		return image;
	}

	/*
	 * 获得字体
	 */
	private Font getFont() {
		//Comic Sans MS
		return new Font("Herculanum", Font.CENTER_BASELINE, 40);
	}

	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}

	/*
	 * 绘制字符串
	 */
	private String drowString(Graphics g, String randomString, int i, char c) {
		g.setFont(getFont());
		g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
		String rand = String.valueOf(c);
		// String rand =
		// String.valueOf(getRandomString(random.nextInt(randString.length())));
		// randomString += rand;
		g.translate(random.nextInt(3), random.nextInt(3));
		g.drawString(rand, 26 * i, 30);
		return randomString;
	}

	/*
	 * 绘制干扰线
	 */
	private void drowLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	private String getRandomString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < checkCodeLength; i++)
			sb.append(getRandomChar());
		return sb.toString();
	}

	/*
	 * 获取随机的字符
	 */
	private char getRandomChar() {
		return randString.charAt((int) (Math.random() * randString.length()));
	}
}
