package org.tinycloud.tinyurl.common.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 字符图形验证码生成工具类
 *
 * @author liuxingyu01
 * @since 2022-08-27 9:43
 **/
public class CaptchaCodeGen {
    /**
     * 随机字符
     */
    private static final String STRING = "ABCDEFGHJKMNOPQRSTUVWXYZabcdefghjkmnopqrstuvwxyz1234567890";

    /**
     * 图片的宽度，默认160
     */
    private int width = 160;

    /**
     * 图片的高度，默认40
     */
    private int height = 40;

    /**
     * 验证码字符个数，默认4个
     */
    private int codeCount = 4;

    /**
     * 验证码干扰线数，默认20，越大越难辨认
     */
    private int lineCount = 20;

    /**
     * 验证码字符
     */
    private String code = null;

    /**
     * 验证码图片Buffer
     */
    private BufferedImage buffImg = null;

    /**
     * 随机数生成器
     * SecureRandom使用更复杂的算法和更安全的随机数生成方法，因此它生成的随机数比Random更难以预测和猜测
     * 但同时带来的问题是性能要慢一点
     */
    SecureRandom random = new SecureRandom();

    /**
     * 构造方法，全部使用默认值
     */
    public CaptchaCodeGen() {
        this.creatImage();
    }

    /**
     * 构造方法，自定义高度和宽度，其他使用默认配置
     *
     * @param width  图片的宽度
     * @param height 图片的高度
     */
    public CaptchaCodeGen(int width, int height) {
        this.width = width;
        this.height = height;
        this.creatImage();
    }

    /**
     * 构造方法，自定义高度和宽度和字符数量，其他使用默认配置
     *
     * @param width     图片的宽度
     * @param height    图片的高度
     * @param codeCount 字符的数量
     */
    public CaptchaCodeGen(int width, int height, int codeCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.creatImage();
    }

    /**
     * 构造方法，自定义高度和宽度和字符数量和干扰线数量，其他使用默认配置
     *
     * @param width     图片的宽度
     * @param height    图片的高度
     * @param codeCount 字符的数量
     * @param lineCount 干扰线的数量
     */
    public CaptchaCodeGen(int width, int height, int codeCount, int lineCount) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.creatImage();
    }

    /**
     * 构造方法，自定义所有参数，包括code验证码随机字符（这意味着需要自己生成随机码）
     *
     * @param width     图片的宽度
     * @param height    图片的高度
     * @param codeCount 字符的数量
     * @param lineCount 干扰线的数量
     * @param code      验证码随机字符
     */
    public CaptchaCodeGen(int width, int height, int codeCount, int lineCount, String code) {
        this.width = width;
        this.height = height;
        this.codeCount = codeCount;
        this.lineCount = lineCount;
        this.creatImage(code);
    }


    /**
     * 生成图片，自动生成随机字符
     */
    private void creatImage() {
        // 得到随机字符
        String code = randomStr(this.codeCount);
        this.creatImage(code);
    }


    /**
     * 根据code生成指定字符图片
     *
     * @param code 字符
     */
    private void creatImage(String code) {
        if (code == null || code.isEmpty()) {
            throw new RuntimeException("验证码字符不能为空");
        }
        // 字体的宽度
        int fontWidth = this.width / this.codeCount;
        // 字体的高度
        int fontHeight = this.height - 5;
        int codeY = this.height - 8;

        // 图像buffer
        this.buffImg = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) this.buffImg.getGraphics();
        // Graphics2D g = buffImg.createGraphics();
        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, this.width, this.height);

        // 设置字体
        // Font font1 = getFont(fontHeight);
        Font font = new Font("Arial", Font.BOLD, fontHeight);
        g.setFont(font);

        // 设置干扰线
        for (int i = 0; i < this.lineCount; i++) {
            int xs = this.random.nextInt(this.width);
            int ys = this.random.nextInt(this.height);
            int xe = xs + this.random.nextInt(this.width);
            int ye = ys + this.random.nextInt(this.height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点 噪声率
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * this.width * this.height);
        for (int i = 0; i < area; i++) {
            int x = this.random.nextInt(width);
            int y = this.random.nextInt(height);
            this.buffImg.setRGB(x, y, this.random.nextInt(255));
        }

        this.code = code;
        for (int i = 0; i < code.length(); i++) {
            String strRand = code.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            // g.drawString(a,x,y);
            // a为要画出来的东西，x和y表示要画的东西最左侧字符的基线位于此图形上下文坐标系的 (x, y) 位置处
            g.drawString(strRand, i * fontWidth + 3, codeY);
        }
    }


    /**
     * 得到随机字符
     *
     * @param n 数量
     * @return n位的随机字符
     */
    public String randomStr(int n) {
        String str = "";
        int len = STRING.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = this.random.nextDouble() * len;
            str = str + STRING.charAt((int) r);
        }
        return str;
    }

    /**
     * 得到随机颜色
     *
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        // 给定范围获得随机颜色
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + this.random.nextInt(bc - fc);
        int g = fc + this.random.nextInt(bc - fc);
        int b = fc + this.random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    private Font getFont(int size) {
        Font[] font = new Font[5];
        font[0] = new Font("Ravie", Font.BOLD, size);
        font[1] = new Font("Antique Olive Compact", Font.BOLD, size);
        font[2] = new Font("Arial", Font.BOLD, size);
        font[3] = new Font("Wide Latin", Font.BOLD, size);
        font[4] = new Font("Courier", Font.BOLD, size);
        return font[this.random.nextInt(5)];
    }

    // 扭曲方法
    private void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private void shearX(Graphics g, int w1, int h1, Color color) {
        int period = this.random.nextInt(2);

        boolean borderGap = true;
        int frames = 1;
        int phase = this.random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }

    }

    private void shearY(Graphics g, int w1, int h1, Color color) {
        // 50
        int period = this.random.nextInt(40) + 10;

        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (double) (period >> 1)
                    * Math.sin((double) i / (double) period
                    + (6.2831853071795862D * (double) phase)
                    / (double) frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }


    /**
     * 返回生成验证码的base64编码，注意是不带data:image/png;base64,前缀的
     * ByteArrayOutputStream不需要关闭
     *
     * @return base64编码
     */
    public String getBase64() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(this.buffImg, "png", outputStream);
            this.buffImg.flush();
            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            // 删除 \r\n
            base64 = base64.trim();
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
            return base64;
        } catch (Exception e) {
            return null;
        } finally {
            // 释放内存，防止内存溢出的问题
            this.buffImg.flush();
            this.buffImg.getGraphics().dispose();
            this.buffImg = null;
        }
    }

    /**
     * 返回生成验证码的base64编码，注意是带data:image/png;base64,前缀的
     * ByteArrayOutputStream不需要关闭
     *
     * @return base64编码
     */
    public String getBase64WithHeader() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(this.buffImg, "png", outputStream);
            String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
            // 删除 \r\n
            base64 = base64.trim();
            base64 = base64.replaceAll("\n", "").replaceAll("\r", "");
            return "data:image/png;base64," + base64;
        } catch (Exception e) {
            return null;
        } finally {
            // 释放内存，防止内存溢出的问题
            this.buffImg.flush();
            this.buffImg.getGraphics().dispose();
            this.buffImg = null;
        }
    }


    /**
     * 获取验证码图片BufferedImage
     *
     * @return BufferedImage
     */
    public BufferedImage getBuffImg() {
        return this.buffImg;
    }


    /**
     * 获取验证码字符
     *
     * @return String
     */
    public String getCode() {
        return this.code;
    }
}
