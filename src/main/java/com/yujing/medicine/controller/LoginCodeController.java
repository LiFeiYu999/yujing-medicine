package com.yujing.medicine.controller;

import com.yujing.medicine.utils.ImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/21 17:08
*/
@Controller
public class LoginCodeController {
    /**
     * 生成图像验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @GetMapping("/checkCode")
    public void checkCode(HttpServletRequest request, HttpServletResponse response)throws IOException {
        //设置编码格式
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 设置不缓存图片
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "No-cache");
        response.setDateHeader("Expires", 0);
        // 指定生成的响应图片,一定不能缺少这句话,否则错误.
        response.setContentType("image/jpeg");
        /*
         * @param
         * 传入生成图像长度和宽度，还有本次会话的请求HttpServletRequest对象request
         * @return
         * 返回一个BufferedImage对象
         * */
        BufferedImage image = ImageUtil.getPicture(80, 35, request);
        // 将图像输出到response输出流中。
        //response.getOutputStream()获取response输出流
        ImageIO.write(image, "JPEG", response.getOutputStream()); // 输出图片
    }

}
