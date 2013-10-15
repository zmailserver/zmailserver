#
# spec file for zmail.rpm
#
Summary: Zmail MTA
Name: zmail-mta
Version: @@VERSION@@
Release: @@RELEASE@@
License: Various
Group: Applications/Messaging
URL: http://www.zmail.com
Vendor: Zmail, Inc.
Packager: Zmail, Inc.
BuildRoot: /opt/zmail
AutoReqProv: no
provides: @@MTA_PROVIDES@@
requires: zmail-core

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
