#
# spec file for zmail.rpm
#
Summary: Zmail QA Tests
Name: zmail-qatest
Version: @@VERSION@@
Release: @@RELEASE@@
License: ZPL
Group: Applications/Messaging
URL: http://www.zmail.com
Vendor: Zmail, Inc.
Packager: Zmail, Inc.
BuildRoot: /opt/zmail
AutoReqProv: no
requires: zmail-core

%description
Best email money can buy

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
