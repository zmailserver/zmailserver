%define evolution_version 2.3.7
%define eds_version 1.3.7
%define evo_api_version 2.8

Summary: Zmail Connector for Evolution %{evo_api_version}
Name: evolution-zmail
Version: @@VERSION@@
Release: @@RELEASE@@
License: GPL
URL: http://www.zmail.com/evolution-connector.htm
Group: System Environment/Libraries

Source: http://www.zmail.com/work/%{name}-%{version}.tar.gz
Vendor: Zmail
Packager: Scott Herscher <scott.herscher@zmail.com>
BuildRoot: %{_tmppath}/%{name}-root

Provides:  evolution-zmail

BuildRequires:  evolution-devel >= %{evolution_version}
BuildRequires:  evolution-data-server-devel >= %{eds_version}
BuildRequires:  glib2 >= 2.8.1

Requires:  evolution >= %{evolution_version}
Requires:  evolution-data-server >= %{eds_version}
Requires:  glib2 >= 2.8.1

%description
Use Zmail Connector to use all features provided by the
Zmail mail server in Evolution %{evo_api_version}.

%clean
[ -n $RPM_BUILD_ROOT -a $RPM_BUILD_ROOT != / ] && rm -rf $RPM_BUILD_ROOT

%files
/usr/lib/evolution-data-server-1.2/camel-providers/*
/usr/lib/evolution-data-server-1.2/extensions/*
/usr/lib/evolution/%{evo_api_version}/plugins/*
%changelog
