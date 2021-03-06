/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.imda.fileprocessor;

import org.tinygroup.fileresolver.impl.AbstractFileProcessor;
import org.tinygroup.imda.ModelManager;
import org.tinygroup.imda.config.ModelDefine;
import org.tinygroup.logger.LogLevel;
import org.tinygroup.logger.Logger;
import org.tinygroup.logger.LoggerFactory;
import org.tinygroup.vfs.FileObject;
import org.tinygroup.xstream.XStreamFactory;

import com.thoughtworks.xstream.XStream;

public class ModelDefineFileProcessor extends AbstractFileProcessor {
	private static final String MODEL_DEFINE_FILE = ".modeldefine.xml";
	private static Logger logger = LoggerFactory
			.getLogger(ModelDefineFileProcessor.class);
	private ModelManager manager;
	private String IMDA_DOMAIN = "imda";

	public ModelManager getManager() {
		return manager;
	}

	public void setManager(ModelManager manager) {
		this.manager = manager;
	}

	public boolean isMatch(FileObject fileObject) {
		return fileObject.getFileName().endsWith(MODEL_DEFINE_FILE);
	}

	public void process() {
		XStream xStream = XStreamFactory.getXStream(IMDA_DOMAIN);
		for (FileObject fileObject : deleteList) {
			logger.logMessage(LogLevel.INFO, "正在移除ModelDefine描述文件：[{}]",
					fileObject.getAbsolutePath());
			ModelDefine modelDefine = (ModelDefine)caches.get(fileObject.getAbsolutePath());
			if(modelDefine!=null){
				manager.removeModelDefine(modelDefine);
				caches.remove(fileObject.getAbsolutePath());
			}
			logger.logMessage(LogLevel.INFO, "ModelDefine描述文件：[{}]移除成功。",
					fileObject.getAbsolutePath());
		}
		for (FileObject fileObject : changeList) {
			try {
				logger.logMessage(LogLevel.INFO, "正在加载ModelDefine描述文件：[{}]",
						fileObject.getAbsolutePath());
				ModelDefine oldModelDefine = (ModelDefine)caches.get(fileObject.getAbsolutePath());
				if(oldModelDefine!=null){
					manager.removeModelDefine(oldModelDefine);
				}
				ModelDefine modelDefine = (ModelDefine) xStream
						.fromXML(fileObject.getInputStream());
				manager.addModelDefine(modelDefine);
				caches.put(fileObject.getAbsolutePath(), modelDefine);
				logger.logMessage(LogLevel.INFO, "ModelDefine描述文件：[{}]加载成功。",
						fileObject.getAbsolutePath());
			} catch (Exception e) {
				logger.errorMessage("加载ModelDefine描述文件[{0}]时发生错误。", e,
						fileObject.getAbsolutePath());
			}
		}

	}

}
