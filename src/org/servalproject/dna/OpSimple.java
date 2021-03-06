/**
 * Copyright (C) 2011 The Serval Project
 *
 * This file is part of Serval Software (http://www.servalproject.org)
 *
 * Serval Software is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * This source code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this source code; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package org.servalproject.dna;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class OpSimple implements Operation {
	enum Code{
		Create((byte)0x0f),
		Declined((byte)0x80),
		Ok((byte)0x81),
		Eot((byte)0xff);
		byte code;
		Code(byte code){
			this.code=code;
		}
	}
	private static Map<Byte, Code> codes=new HashMap<Byte, Code>();
	static{
		for (Code c:Code.values()){
			codes.put(c.code, c);
		}
	}
	public static Code getCode(byte b){
		return codes.get(b);
	}
	Code code;

	OpSimple(){}
	public OpSimple(Code code){
		this.code=code;
	}

	@Override
	public void parse(ByteBuffer b, byte code) {
		this.code=getCode(code);
	}

	@Override
	public void write(ByteBuffer b) {
		b.put(code.code);
	}

	@Override
	public boolean visit(Packet packet, OpVisitor v) {
		return v.onSimpleCode(packet, code);
	}

	@Override
	public String toString() {
		return "SimpleCode: "+code.name();
	}
}
