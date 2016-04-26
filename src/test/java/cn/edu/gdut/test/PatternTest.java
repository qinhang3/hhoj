package cn.edu.gdut.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

public class PatternTest {
	@Test
	public void test(){
		String s1 = "^([1-9][0-9]*)$";
		String s2 = "^(0\\.[0-9]*[1-9][0-9]*)$";
		String s3 = "^(([1-9][0-9]*)|([1-9][0-9]*\\.[0-9]{1,2}))$";
		String s4 = "^http(s{0,1})://img\\.taobaocdn\\.com/bao/uploaded/([0-9a-zA-Z_\\.]+)\\.(jpg|JPG|gif|GIF|png|PNG|bmp|BMP)$";
		String s5 = "^([\\s\\S]*[\\S]+[\\s\\S]*)$";
		
		Assert.assertTrue(docheck(s1, "36934535933"));
		Assert.assertFalse(docheck(s1, "36934535933aa"));
		
		Assert.assertTrue(docheck(s2, "0.4698"));
		Assert.assertFalse(docheck(s2, "0.400698a"));
		Assert.assertFalse(docheck(s2, "0."));
		Assert.assertFalse(docheck(s2, "0.."));
		Assert.assertFalse(docheck(s2, "0.00"));
		
		Assert.assertTrue(docheck(s3, "69"));
		Assert.assertTrue(docheck(s3, "69.1"));
		Assert.assertTrue(docheck(s3, "69.01"));
		Assert.assertTrue(docheck(s3, "69.10"));
		Assert.assertFalse(docheck(s3, "69."));
		Assert.assertFalse(docheck(s3, "69.a"));
		
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX.jpg"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX1.JPG"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX11.gif"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX22.GIF"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX33.png"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX44.PNG"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXXa.bmp"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX123.BMP"));
		Assert.assertTrue(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1P_sGHpXXXXXWXFXXSutbFXXX.jpg"));
                      		

		
		Assert.assertFalse(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX123.txt"));
		Assert.assertFalse(docheck(s4, "img.taobaocdn.com/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX123.jpg"));
		Assert.assertFalse(docheck(s4, "http://img.taobaocdn.com/bao/uploaded/.jpg"));
		Assert.assertFalse(docheck(s4, "http://img.taobaocdn.com/bao/uploaded//TB1bswHHpXXXXX5XXXXSutbFXXX123.jpg"));
		Assert.assertFalse(docheck(s4, "http://img.taobaocdncom/bao/uploaded/TB1bswHHpXXXXX5XXXXSutbFXXX123.jpg"));
		
		Assert.assertTrue(docheck(s5, "2015天喜新款孕妇夏装连衣裙孕妇装春装时尚上衣春夏装短裙孕妇裙"));
		Assert.assertTrue(docheck(s5, "2015天喜新款孕妇夏装连衣裙孕妇装春装时尚上衣春夏装短裙孕妇裙 "));
		Assert.assertTrue(docheck(s5, "2015天喜新款孕妇夏装连衣裙孕妇装春装时尚上衣春夏装短裙孕妇裙 abc123"));
		Assert.assertTrue(docheck(s5, "2015天喜新款孕妇夏装连衣裙孕妇装春装时尚上衣春夏装短裙孕妇裙_abc-123"));
		Assert.assertTrue(docheck(s5, "2015天喜新款孕妇夏装连衣裙孕妇装春-装时尚-上衣春夏装短裙孕妇裙"));
		Assert.assertTrue(docheck(s5, "  -  "));
		Assert.assertTrue(docheck(s5, "1    "));
		Assert.assertTrue(docheck(s5, "    1"));
		Assert.assertFalse(docheck(s5, "    "));
		Assert.assertFalse(docheck(s5, "  \t  "));
		Assert.assertFalse(docheck(s5, "  \n  "));
		Assert.assertTrue(docheck(s5, "0-1-3岁汇乐不倒翁玩具摆件大号点头娃娃宝宝音乐玩具婴幼儿早教,20962724721"));
	}
	
	private Boolean docheck(String regex,String str){
		Pattern patten = Pattern.compile(regex);
		Matcher matcher = patten.matcher(str);
		return matcher.matches();
	}

}
