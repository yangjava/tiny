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
package org.tinygroup.entity.extendinfo;

import java.util.Map.Entry;

import org.tinygroup.context.Context;
import org.tinygroup.entity.ViewServiceProcessor;
import org.tinygroup.format.Formater;
import org.tinygroup.format.impl.ContextFormater;
import org.tinygroup.format.impl.FormaterImpl;
import org.tinygroup.tinydb.Bean;
import org.tinygroup.xmlparser.node.XmlNode;

/**
 * 扩展信息中的<fieldMap>节点描述对象
 * 功能说明: 

 * 开发人员: renhui <br>
 * 开发时间: 2013-9-17 <br>
 * <br>
 */
public class FieldMap {
	protected static final String NAME="name";
	protected static final String FIELD_ID="fieldId";
	protected static final String PATTERN="pattern";
	private String name;//属性名称
	private String fieldId;//引用的字段
	private String pattren;//格式化字符串
	private static  Formater formater = new FormaterImpl();
	static{
		formater.addFormatProvider("", new ContextFormater());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFieldId() {
		return fieldId;
	}
	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	public String getPattren() {
		return pattren;
	}
	public void setPattren(String pattren) {
		this.pattren = pattren;
	}
	public void setPropertyWithNode(XmlNode subNode) {
		 this.name=subNode.getAttribute(NAME);
		 this.fieldId=subNode.getAttribute(FIELD_ID);
		 this.pattren=subNode.getAttribute(PATTERN);
	}
	public Object getValue(Bean bean, ViewServiceProcessor processor,Context context) {
		String propertyName=processor.getPropertyName(fieldId);
		Object value=null;
		if(pattren!=null&&pattren.trim().length()>0){
			bean2Context(bean,context);
			value=formater.format(context, pattren);
		}else{
			value=bean.get(propertyName);
		}
		return value;
	}
	
	private void bean2Context(Bean bean, Context context) {
		for (Entry<String, Object> entry : bean.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
	}

	
}
