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
