#ifndef USE_MSG_H
#define USE_MSG_H

enum {
	AM_USBMSG = 0x74,
};

typedef nx_struct USBMsg{
	nx_uint8_t  nodeID;
	nx_uint32_t sequence;
	nx_uint8_t  counter;
}USBMsg;

#endif /* USE_MSG_H */
