package org.arios.net.packet.out;

import org.arios.net.packet.IoBuffer;
import org.arios.net.packet.OutgoingPacket;
import org.arios.net.packet.context.MusicContext;

/**
 * Outgoing Music packet
 * @author SonicForce41
 */
public class MusicPacket implements OutgoingPacket<MusicContext> {

	@Override
	public void send(MusicContext context) {
		IoBuffer buffer = null;
		if (context.isSecondary()) {
			buffer = new IoBuffer(208);
			buffer.putTri(255);
			buffer.putLEShort(context.getMusicId());
		} else {
			buffer = new IoBuffer(4);
			buffer.putLEShortA(context.getMusicId());
		}
		context.getPlayer().getDetails().getSession().write(buffer);
	}

}
