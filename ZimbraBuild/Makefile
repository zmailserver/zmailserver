# Makefile for entire install tree, for RPM packages.

.PHONY: core proxy memcached mta store ldap snmp

# BASE VARIABLES
SHELL	:= /bin/bash
BUILD_ROOT	:= $(shell pwd)

DEFS_DIR := $(BUILD_ROOT)/defs

include $(DEFS_DIR)/globals.def

include $(DEFS_DIR)/devglobals.def

include $(DEFS_DIR)/paths.def

# 3rd PARTY INCLUDES

THIRD_PARTY	:= $(BUILD_ROOT)/../ThirdParty
THIRD_PARTY_BUILDS	:= $(BUILD_ROOT)/../ThirdPartyBuilds

ifneq   (1,$(NOPLATFORMS))
include $(DEFS_DIR)/$(BUILD_PLATFORM).def
endif

include $(DEFS_DIR)/destination.def

# COMPONENTS

include $(DEFS_DIR)/components.def

# PACKAGE TARGETS

all: packages patch zcs-$(RELEASE).$(BUNDLE_EXT)

include $(DEFS_DIR)/misctargets.def

include $(DEFS_DIR)/releasetargets.def

include $(DEFS_DIR)/coretargets.def

include $(DEFS_DIR)/memcachedtargets.def

include $(DEFS_DIR)/proxytargets.def

include $(DEFS_DIR)/ldaptargets.def

ifeq ($(BUILD_PLATFORM), RHEL4_64)
include $(DEFS_DIR)/mtatargets-rhel4.def
else
include $(DEFS_DIR)/mtatargets.def
endif

include $(DEFS_DIR)/loggertargets.def

include $(DEFS_DIR)/apachetargets.def

include $(DEFS_DIR)/storetargets.def

include $(DEFS_DIR)/webapptargets.def

include $(DEFS_DIR)/jartargets.def

include $(DEFS_DIR)/snmptargets.def

include $(DEFS_DIR)/sourcetargets.def

include $(DEFS_DIR)/patchtargets.def

include $(DEFS_DIR)/spelltargets.def

include $(DEFS_DIR)/devtargets.def

include $(DEFS_DIR)/clean.def

include $(DEFS_DIR)/devclean.def

ifeq (MACOSXx86,$(findstring MACOSXx86,$(BUILD_PLATFORM)))
include $(DEFS_DIR)/isync.def
include $(DEFS_DIR)/app-mactoaster.def
endif
