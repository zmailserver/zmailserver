#
# spec file for zimbra.rpm
#
Summary: Zimbra Core
Name: zimbra-core
Version: @@VERSION@@
Release: @@RELEASE@@
License: Various
Group: Applications/Messaging
URL: http://www.zimbra.com
Vendor: Zimbra, Inc.
Packager: Zimbra, Inc.
BuildRoot: /opt/zimbra
AutoReqProv: no

%description
Best email money can buy

%define __spec_install_post /usr/lib/rpm/brp-compress /usr/lib/rpm/brp-strip-comment-note %{nil}

%prep

%build

rm -rf $RPM_BUILD_ROOT.tmp
mv $RPM_BUILD_ROOT $RPM_BUILD_ROOT.tmp

%install

mv $RPM_BUILD_ROOT.tmp/* $RPM_BUILD_ROOT
rm -rf $RPM_BUILD_ROOT.tmp

%pre

%post

%preun

%postun

%files
