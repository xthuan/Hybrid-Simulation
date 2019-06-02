COMPONENT=MasterAppC

TOSMAKE_PRE_EXE_DEPS = USBMsg.class
TOSMAKE_CLEAN_EXTRA = *.class *.java

USBMsg.class: USBMsg.java
	javac USBMsg.java

USBMsg.java: USBMsg.h
	nescc-mig java $(NESC_PFLAGS) $(CFLAGS) -java-classname=USBMsg USBMsg.h USBMsg -o $@

CFLAGS += -DTOSH_DATA_LENGTH=110
CFLAGS += -DPACKET_LINK
CFLAGS += -I$(TINYOS_OS_DIR)/lib/printf
#CFLAGS += -I$(TOSDIR)/lib/printf
PFLAGS += -DCC2420_DEF_CHANNEL=20
# PFLAGS += -DCC2420_NO_ACKNOWLEDGEMENTS

TINYOS_ROOT_DIR?=../..

include $(TINYOS_ROOT_DIR)/Makefile.include