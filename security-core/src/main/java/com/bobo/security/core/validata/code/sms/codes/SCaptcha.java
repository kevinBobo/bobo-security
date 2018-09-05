package com.bobo.security.core.validata.code.sms.codes;


import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Random;

/**
 * 验证码
 * Description:
 * All Rights Reserved.
 *
 * @version 1.0  2015-2-4 下午03:44:49  by 王冠华（wangguanhua@dangdang.com）创建
 */
@Slf4j
public class SCaptcha implements Serializable {

    private static final long serialVersionUID = -6149930282728414838L;

    //	// 图片的宽度。
//	private int width = ConfigPropertieUtils.getInteger("captcha.width", 102);
//	// 图片的高度。
//	private int height = ConfigPropertieUtils.getInteger("captcha.height", 42);
//	// 验证码字符个数
//	private int codeCount = ConfigPropertieUtils.getInteger("captcha.codeCount", 4);
//	// 验证码干扰线数
//	private int lineCount = ConfigPropertieUtils.getInteger("captcha.lineCount", 5);
    // 图片的宽度。
    private int width = 102;
    // 图片的高度。
    private int height = 42;
    // 验证码字符个数
    private int codeCount = 4;
    // 验证码干扰线数
    private int lineCount = 5;
    // 验证码
    private String code = null;
    // 验证码图片Buffer
    private BufferedImage buffImg = null;
    /**
     * 字体
     */
//	private String[] fonts = ApiConstant.CAPTCHA_FONTS.split(",");
    /**
     * 缓存时间
     */
//	private static final int CACHE_TIMEOUT = ConfigPropertieUtils.getInteger("captcha.cache.timeout", 300);
    /**
     * 字体
     */
    private static String[] fonts = {"迷你简娃娃篆", "儿童卡通字体"};
    private static String defaultFont = "宋体";
    /**
     * 缓存时间
     */
    private static final int CACHE_TIMEOUT = 300;

//	static {
//		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
//		fonts = environment.getAvailableFontFamilyNames();//获得系统字体
//	}

    // 生成随机数
    private Random random = new Random();

    public SCaptcha(String randomCode) {
        this.createCode(randomCode);
    }

    /**
     * @param width  图片宽
     * @param height 图片高
     */
    public SCaptcha(int width, int height, String randomCode) {
        this.width = width;
        this.height = height;
        this.createCode(randomCode);
    }

    /**
     * @param width     图片宽
     * @param height    图片高
     * @param codeCount 字符个数
     * @param lineCount 干扰线条数
     */
    public SCaptcha(int width, int height, int codeCount, int lineCount, String randomCode) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.createCode(randomCode);
    }

    public void createCode(String rCode) {
        int codeX = 0;
        int fontHeight = 0;
        fontHeight = height - 17;// 字体的高度
        codeX = width / (codeCount + 1);// 每个字符的宽度

//		// 获取系统字体
//		getEnvironmentFont();

        // 图像buffer
        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        //设置透明  start
        buffImg = g.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g = buffImg.createGraphics();
        //设置透明  end

        // 将图像填充为透明
        g.setColor(Color.WHITE);
        //g.fillRect(0, 0, width, height);

        // 绘制干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = getRandomNumber(width);
            int ys = getRandomNumber(height);
            int xe = xs + getRandomNumber(width / 8);
            int ye = ys + getRandomNumber(height / 8);
            g.setColor(getRandomColor());
            g.drawLine(xs, ys, xe, ye);
        }
        // 设置字体颜色
        g.setColor(Color.BLACK);

        StringBuffer randomCode = new StringBuffer();
        //String strRand = CaptchaUtil.getRandomCode();
        char[] cs = rCode.toCharArray();
        // 随机产生验证码字符
        for (int i = 0; i < cs.length; i++) {
            String temp = String.valueOf(cs[i]);
            // 创建字体
            g.setFont(getRandomFont(fontHeight, cs[i]));
            // 设置字体位置
            g.drawString(temp, i * codeX + 8,
                    getRandomNumber(height / 2) + 20);
            randomCode.append(temp);
        }
        code = randomCode.toString();
    }

    /**
     * 获取随机颜色
     */
    private Color getRandomColor() {
        return new Color(35, 79, 166);
    }

    /**
     * 获取随机数
     */
    private int getRandomNumber(int number) {
        return random.nextInt(number);
    }

    public void write(String path) throws IOException {
        OutputStream sos = new FileOutputStream(path);
        this.write(sos);
    }

    public void write(OutputStream sos) throws IOException {
        ImageIO.write(buffImg, "png", sos);
        if (sos != null) {
            sos.close();
        }
    }

    public BufferedImage getBuffImg() {
        return buffImg;
    }

    public String getCode() {
        return code;
    }

    /**
     * 将code放入缓存 Description:
     * @Version1.0 2015-2-2 下午03:30:05 by 王冠华（wangguanhua@dangdang.com）创建
     * @param deviceNo
     * Notice:已经废弃, 改为 dubbo服务对外提供
     */
//	@Deprecated
//	public void putCode2Cache(String deviceNo) {
//		MemcachedManager.set(ApiConstant.CAPTCHA_CACHE_PREFIX + deviceNo, code,
//				CACHE_TIMEOUT);
//	}

    /**
     * 随机字体
     * Description:
     *
     * @param fontHeight
     * @return
     * @Version1.0 2015-2-4 下午03:22:37 by 王冠华（wangguanhua@dangdang.com）创建
     */
    public Font getRandomFont(int fontHeight, char cs) {
        int fontIndex = (int) Math.round(Math.random() * (fonts.length - 1));
        Font font = new Font(fonts[fontIndex], Font.PLAIN, fontHeight);
        if (!font.canDisplay(cs)) {
            font = new Font(defaultFont, Font.PLAIN, fontHeight);
        }
        return font;
    }
}
