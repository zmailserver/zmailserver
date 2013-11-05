/*
    Copyright 2013 MLstate

    Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/

package com.proviciel.db;

import java.util.Map;

import com.google.common.collect.ImmutableMap;

public class JournalItem {

	public enum Event {
		NEW(1),
		READ(2),
		UNREAD(3),
		FLAG(4),
		UNFLAG(5),
		DELETE(6),
		MOVE(7),
		COPY(8);
		
        private static final Map<Byte, Event> M;
        static {
            ImmutableMap.Builder<Byte, Event> builder = ImmutableMap.builder();
            for (Event e : Event.values()) {
                builder.put(e.toByte(), e);
            }
            M = builder.build();
        }
		private byte bevent;
		
		private Event(int b){
			bevent = (byte) b;
		}
		
		public byte toByte(){
			return bevent;
		}
		
		public static Event of(byte b){
			return M.get(b);
		}
	}
	public int jid;
	
	public int item_id;
	
	public int more;
	
	public long date;
	
	public Event event;
	
	public JournalItem(int jid, int item_id, long date, Event event, int more){
		this.jid = jid;
		this.event = event;
		this.item_id = item_id;
		this.date = date;
		this.more = more;
	}
	
	public JournalItem(int jid, int item_id, long date, byte event, int more){
		this.jid = jid;
		this.event = Event.of(event);
		this.item_id = item_id;
		this.date = date;
		this.more = more;
	}
}
