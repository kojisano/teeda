/*
 * Copyright 2004-2006 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, 
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package javax.faces.model;

/**
 * TODO TEST
 */

class ResultSetKeysIterator_ extends ResultSetBaseIterator_ {

	public ResultSetKeysIterator_(ResultSetDataModel.ResultSetMap map){
		super(map);
		super.itr_ = super.map_.realKeys();
	}
	
	public boolean hasNext() {
		return super.itr_.hasNext();
	}
	public Object next() {
		return super.itr_.next();
	}
}
