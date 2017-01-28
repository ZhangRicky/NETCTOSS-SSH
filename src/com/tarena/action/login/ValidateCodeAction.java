package com.tarena.action.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.tarena.action.BaseAction;
import com.tarena.util.ImageUtil;

/**
 * ¸ü»»ÑéÖ¤Âë
 * @author Ricky
 *
 */
@Controller
@Scope("prototype")
public class ValidateCodeAction extends BaseAction {

	private InputStream codeStream;

	public String execute() {
		Map<String, BufferedImage> imageMap = ImageUtil.createImage();
		String code = imageMap.keySet().iterator().next();
		session.put("imageCode", code);
		
		BufferedImage image = imageMap.get(code);
		try {
			codeStream = ImageUtil.getInputStream(image);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}

		return "success";
	}

	public InputStream getCodeStream() {
		return codeStream;
	}

	public void setCodeStream(InputStream codeStream) {
		this.codeStream = codeStream;
	}

}
