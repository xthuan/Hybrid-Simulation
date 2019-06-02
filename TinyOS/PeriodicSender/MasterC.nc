#include <Timer.h>
#include "USBMsg.h"

module MasterC {
		uses interface Boot;
		uses interface Leds;
	  uses interface SplitControl as SerialControl;
	  uses interface AMSend as UartSend;
	  uses interface Packet as UartPacket;
		uses interface Receive as UartReceive;
	  uses interface Timer<TMilli> as USBTimer;
}

implementation{

  USBMsg    usb_msg;
  message_t usb_buffer;
  uint32_t  usb_seq = 0;
  uint8_t   usb_counter = 0;

  void setLeds(uint8_t val) {
    if (val == 1)
      call Leds.led0On();
    else 
      call Leds.led0Off();
    if (val == 2)
      call Leds.led1On();
    else
      call Leds.led1Off();
    if (val == 3)
      call Leds.led2On();
    else
      call Leds.led2Off();
  }

	event void Boot.booted(){
		call SerialControl.start();
	}

	event void SerialControl.startDone(error_t error) {
      	if(error != SUCCESS){
        	call SerialControl.start();
      	}else{
      		call USBTimer.startPeriodic(5000);
      	}
	}

	event void SerialControl.stopDone(error_t error) {
	}

	void USBLog(){
        USBMsg *umsg = (USBMsg*)call UartPacket.getPayload(&usb_buffer, sizeof(USBMsg));
        umsg->nodeID = TOS_NODE_ID;
        umsg->sequence = usb_seq;
        // umsg->counter  = usb_counter;
        if( (call UartSend.send(AM_BROADCAST_ADDR, &usb_buffer, sizeof(USBMsg))) == SUCCESS ){
        }
  }

	event void USBTimer.fired(){
        USBLog();
        usb_seq++;
  }

  event message_t* UartReceive.receive(message_t* msg,void* payload,uint8_t len){
      USBMsg* btrpkt = (USBMsg*)payload;
      setLeds(btrpkt->counter);
  		return msg;
  }

	event void UartSend.sendDone(message_t *msg, error_t error){
  		call Leds.led1Toggle();
      usb_counter = 0;
	}

}
