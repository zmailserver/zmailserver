MYSQL_ROOT := $(shell pwd)
P4_ROOT ?= $(shell cd $(MYSQL_ROOT)/../..; pwd)

BUILD_PLATFORM ?= $(shell sh $(P4_ROOT)/ZimbraBuild/rpmconf/Build/get_plat_tag.sh)

MAKE ?= make
MAKEARGS ?= -j2

ifeq ($(BUILD_PLATFORM), )
	BUILD_PLATFORM := "UNKNOWN"
endif

ZIMBRA_HOME ?= /opt/zimbra

ifdef BETA
	include $(MYSQL_ROOT)/../beta_versions.def
	PATCH   := patch -g0 -N -p1 < ../patches/openssl.patch; patch -g0 -N -p1 < ../patches/apple-runpath.patch;
else
	include $(MYSQL_ROOT)/../versions.def
	PATCH   := patch -g0 -N -p1 < ../patches/openssl.patch; patch -g0 -N -p1 < ../patches/apple-runpath.patch;
endif

MYSQL_VERSION ?= standard-$(MYSQL_VERSION_STRING)-pc-linux-gnu-i686-glibc23

LDFLAGS	:= LDFLAGS="$(PLAT_LDFLAGS) -L$(ZIMBRA_HOME)/mysql-$(MYSQL_VERSION)/lib -R$(ZIMBRA_HOME)/mysql-$(MYSQL_VERSION)/lib -R$(ZIMBRA_HOME)/lib -R$(OPENSSL_LIB_DIR)"
ifeq ($(BUILD_PLATFORM), UBUNTU12_64)
	LDFLAGS	:= LDFLAGS="$(PLAT_LDFLAGS) -L$(ZIMBRA_HOME)/mysql-$(MYSQL_VERSION)/lib"
endif

MYSQL_TGZ_TARGET := \
	$(P4_ROOT)/ThirdPartyBuilds/$(BUILD_PLATFORM)/mysql/mysql-$(MYSQL_VERSION).tar.gz

MYSQL_LIB_TGZ_TARGET := \
	$(P4_ROOT)/ThirdPartyBuilds/$(BUILD_PLATFORM)/mysql/mysql-libs-$(MYSQL_VERSION).tgz

files   := $(wildcard src/mysql-$(MYSQL_VERSION_STRING).tar.gz)

all: checksrc allclean build tar

checksrc:
	$(if $(files), @echo "", exit 1)

clean:
	rm -rf mysql-$(MYSQL_VERSION_STRING)

build:
	echo $(OS_RELEASE) $(OS_PLATFORM)
	mkdir -p $(ZIMBRA_HOME)
	rm -rf mysql-$(MYSQL_VERSION_STRING)
	tar xzf src/mysql-$(MYSQL_VERSION_STRING).tar.gz
	(cd mysql-$(MYSQL_VERSION_STRING); \
	$(PATCH) \
	$(MAC_TARGET) CFLAGS="-g -O3 $(PLAT_CFLAGS)" CXXFLAGS="-g -O3 $(PLAT_CXX_FLAGS) -felide-constructors -fno-exceptions -fno-rtti" \
	$(LDFLAGS) \
	CPPFLAGS="-I/opt/zimbra/openssl-$(OPENSSL_VERSION)/include" \
	/usr/bin/cmake . -DCMAKE_SKIP_BUILD_RPATH=FALSE -DCMAKE_BUILD_WITH_INSTALL_RPATH=FALSE -DCMAKE_INSTALL_RPATH="/opt/zimbra/openssl-$(OPENSSL_VERSION)/lib" -DCMAKE_PREFIX_PATH=/opt/zimbra/openssl-$(OPENSSL_VERSION) -DWITH_SSL=system -DCMAKE_INSTALL_PREFIX=/opt/zimbra/mysql-$(MYSQL_VERSION) -DENABLED_PROFILING=on -DWITH_READLINE=TRUE; \
	LD_RUN_PATH=$(OPENSSL_LIB_DIR) $(MAKE) $(MAKEARGS); \
	$(MAKE) install)
	(cd $(ZIMBRA_HOME); rm -f mysql; ln -s mysql-$(MYSQL_VERSION) mysql)

tar:
	-mkdir -p $(shell dirname $(MYSQL_TGZ_TARGET))
	(cd $(ZIMBRA_HOME); tar czvf $(MYSQL_LIB_TGZ_TARGET) mysql-$(MYSQL_VERSION)/lib)
	(cd $(ZIMBRA_HOME); tar czvf $(MYSQL_TGZ_TARGET) mysql-$(MYSQL_VERSION)/bin mysql-$(MYSQL_VERSION)/include mysql-$(MYSQL_VERSION)/mysql-test mysql-$(MYSQL_VERSION)/scripts mysql-$(MYSQL_VERSION)/share mysql-$(MYSQL_VERSION)/sql-bench mysql-$(MYSQL_VERSION)/support-files)

p4edit: $(MYSQL_TGZ_TARGET) 
	p4 add $(MYSQL_TGZ_TARGET) $(MYSQL_LIB_TGZ_TARGET)
	p4 edit $(MYSQL_TGZ_TARGET) $(MYSQL_LIB_TGZ_TARGET)

allclean: clean
	rm -rf $(ZIMBRA_HOME)/mysql-$(MYSQL_VERSION)
	rm -rf $(ZIMBRA_HOME)/mysql
	rm -rf $(ZIMBRA_HOME)/mysql-$(MYSQL_VERSION)
	rm -f $(MYSQL_TGZ_TARGET)
	rm -f $(MYSQL_LIB_TGZ_TARGET)
