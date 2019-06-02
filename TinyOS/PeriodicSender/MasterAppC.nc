#include <Timer.h>
#include "USBMsg.h"

configuration MasterAppC{
}

implementation {
		
 	components MainC;
	components LedsC;
	components MasterC as App;
	components SerialActiveMessageC;
	components new TimerMilliC() as USBTimer;
	components new SerialAMSenderC(AM_USBMSG) as UartSender;
	components new SerialAMReceiverC(AM_USBMSG) as UartReceiver;

	App.Boot            -> MainC;
	App.Leds            -> LedsC;
	
	App.USBTimer  		-> USBTimer;
	
	App.SerialControl 	-> SerialActiveMessageC;
  	App.UartSend      	-> UartSender;
  	App.UartPacket    	-> UartSender;
  	App.UartReceive     -> UartReceiver;
}