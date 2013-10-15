/*
 * ***** BEGIN LICENSE BLOCK *****
 * Zimbra Collaboration Suite Web Client
 * Copyright (C) 2007, 2008, 2009, 2010, 2011, 2012 VMware, Inc.
 * 
 * The contents of this file are subject to the Zimbra Public License
 * Version 1.3 ("License"); you may not use this file except in
 * compliance with the License.  You may obtain a copy of the License at
 * http://www.zimbra.com/license.
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied.
 * ***** END LICENSE BLOCK *****
 */
AjxPackage.require("zmailAdmin.common.ZaUtil");
AjxPackage.require("zmailAdmin.common.ZaEvent");
AjxPackage.require("zmailAdmin.common.ZaModel");
AjxPackage.require("zmailAdmin.common.ZaItem");
AjxPackage.require("zmailAdmin.common.ZaId");
AjxPackage.require("zmailAdmin.common.ZaIPUtil");
AjxPackage.require("zmailAdmin.common.Lifetime_XFormItem");
AjxPackage.require("zmailAdmin.config.settings.ZaSettings");
AjxPackage.require("zmailAdmin.common.ZaAppCtxt");
AjxPackage.require("zmailAdmin.common.ZaAuthenticate");
AjxPackage.require("zmailAdmin.common.ZaPopupMenu");
AjxPackage.require("zmailAdmin.common.ZaAppViewMgr");
AjxPackage.require("zmailAdmin.common.ZaLoginDialog");
AjxPackage.require("zmailAdmin.common.ZaController");
AjxPackage.require("zmailAdmin.common.ZaXFormViewController");
AjxPackage.require("zmailAdmin.common.ZaListViewController");
AjxPackage.require("zmailAdmin.common.ZaItemVector");
AjxPackage.require("zmailAdmin.common.ZaItemList");
AjxPackage.require("zmailAdmin.common.ZaListView");
AjxPackage.require("zmailAdmin.common.ZaToolBar");
AjxPackage.require("zmailAdmin.common.ZaToolBarLabel");
AjxPackage.require("zmailAdmin.common.ZaToolBarButton");
AjxPackage.require("zmailAdmin.common.ZaOverviewPanel");
AjxPackage.require("zmailAdmin.common.ZaClientCmdHandler");
AjxPackage.require("zmailAdmin.common.ZaApp");
AjxPackage.require("zmailAdmin.common.ZaAboutDialog");
AjxPackage.require("zmailAdmin.common.ZaMsgDialog");
AjxPackage.require("zmailAdmin.common.ZaErrorDialog");
AjxPackage.require("zmailAdmin.common.ZaTabView");
AjxPackage.require("zmailAdmin.common.ZaXDialog");
AjxPackage.require("zmailAdmin.common.ZaXWizardDialog");
AjxPackage.require("zmailAdmin.common.LDAPURL_XFormItem");
AjxPackage.require("zmailAdmin.common.HostPort_XFormItem");
AjxPackage.require("zmailAdmin.common.MailQuota_XModelItem");
AjxPackage.require("zmailAdmin.common.ZaSelectRadioXFormItem");
AjxPackage.require("zmailAdmin.common.ZaZimletSelectXFormItem");
AjxPackage.require("zmailAdmin.common.ZaCheckBoxListXFormItem");
AjxPackage.require("zmailAdmin.common.Signature_XFormItem");
AjxPackage.require("zmailAdmin.common.Super_XFormItems");
AjxPackage.require("zmailAdmin.common.ZaSplashScreen");
AjxPackage.require("zmailAdmin.common.ZaCurrentAppToolBar");
AjxPackage.require("zmailAdmin.common.ZaCrtAppTreeHeader");
AjxPackage.require("zmailAdmin.common.ZaServerVersionInfo");
AjxPackage.require("zmailAdmin.common.MenuButton_XFormItem");
AjxPackage.require("zmailAdmin.common.ZaAutoCompleteListView");
AjxPackage.require("zmailAdmin.common.AutoComplete_XFormItem");
AjxPackage.require("zmailAdmin.common.ZaKeyMap");
AjxPackage.require("zmailAdmin.common.ACLXFormItem");
AjxPackage.require("zmailAdmin.common.ZaSkinPoolChooser");
AjxPackage.require("zmailAdmin.common.ZaZimletPoolChooser");
AjxPackage.require("zmailAdmin.common.ZaXProgressDialog");
AjxPackage.require("zmailAdmin.common.ZaAppTabGroup");
AjxPackage.require("zmailAdmin.common.ZaAppTab");
AjxPackage.require("zmailAdmin.common.ZaRequestMgr");
AjxPackage.require("zmailAdmin.common.ZaActionStatusView");
AjxPackage.require("zmailAdmin.common.ZaTreeItem");
AjxPackage.require("zmailAdmin.common.ZaTree");
AjxPackage.require("zmailAdmin.common.ZaCurrentAppBar");
AjxPackage.require("zmailAdmin.common.ZaHistoryMgr");

//
// Admin UI Specific components
//

// controllers
AjxPackage.require("zmailAdmin.common.ZaOverviewPanelController");
AjxPackage.require("zmailAdmin.common.ZaOperation");
AjxPackage.require("zmailAdmin.home.controller.ZaHomeController");
AjxPackage.require("zmailAdmin.accounts.controller.ZaAccountListController");
AjxPackage.require("zmailAdmin.accounts.controller.ZaAccountViewController");
//AjxPackage.require("zmailAdmin.accounts.controller.ZaAccAliasesController");
AjxPackage.require("zmailAdmin.cos.controller.ZaCosListController");
AjxPackage.require("zmailAdmin.cos.controller.ZaCosController");
AjxPackage.require("zmailAdmin.domains.controller.ZaDomainListController");
AjxPackage.require("zmailAdmin.servers.controller.ZaServerListController");
AjxPackage.require("zmailAdmin.servers.controller.ZaServerController");
AjxPackage.require("zmailAdmin.adminext.controller.ZaAdminExtListController");
AjxPackage.require("zmailAdmin.zimlets.controller.ZaZimletListController");
AjxPackage.require("zmailAdmin.zimlets.controller.ZaZimletViewController");
AjxPackage.require("zmailAdmin.domains.controller.ZaDomainController");
AjxPackage.require("zmailAdmin.status.controller.ZaStatusViewController");
AjxPackage.require("zmailAdmin.statistics.controller.ZaGlobalStatsController");
AjxPackage.require("zmailAdmin.statistics.controller.ZaServerStatsController");
AjxPackage.require("zmailAdmin.globalconfig.controller.ZaGlobalConfigViewController");
AjxPackage.require("zmailAdmin.dl.controller.ZaDLController");
AjxPackage.require("zmailAdmin.resource.controller.ZaResourceController");
AjxPackage.require("zmailAdmin.helpdesk.controller.ZaHelpViewController");
AjxPackage.require("zmailAdmin.helpdesk.controller.ZaMWizController");
AjxPackage.require("zmailAdmin.mta.controller.ZaMTAListController");
AjxPackage.require("zmailAdmin.mta.controller.ZaMTAController");
AjxPackage.require("zmailAdmin.search.controller.ZaSearchListController");
AjxPackage.require("zmailAdmin.search.controller.ZaSearchBuilderController");
AjxPackage.require("zmailAdmin.task.controller.ZaTaskController");

// model
AjxPackage.require("zmailAdmin.home.model.ZaHome");
AjxPackage.require("zmailAdmin.accounts.model.ZaDataSource");
AjxPackage.require("zmailAdmin.accounts.model.ZaAccount");
AjxPackage.require("zmailAdmin.dl.model.ZaDistributionList");
AjxPackage.require("zmailAdmin.resource.model.ZaSignature");
AjxPackage.require("zmailAdmin.resource.model.ZaResource");
AjxPackage.require("zmailAdmin.resource.model.ZaContactList");
AjxPackage.require("zmailAdmin.accounts.model.ZaAlias");
AjxPackage.require("zmailAdmin.accounts.model.ZaForwardingAddress");
AjxPackage.require("zmailAdmin.accounts.model.ZaFp");
AjxPackage.require("zmailAdmin.cos.model.ZaCos");
AjxPackage.require("zmailAdmin.domains.model.ZaDomain");
AjxPackage.require("zmailAdmin.search.model.ZaSearch");
AjxPackage.require("zmailAdmin.search.model.ZaSearchOption");
AjxPackage.require("zmailAdmin.servers.model.ZaServer");
AjxPackage.require("zmailAdmin.zimlets.model.ZaZimlet");
AjxPackage.require("zmailAdmin.rp.model.ZaRetentionPolicy");
AjxPackage.require("zmailAdmin.globalconfig.model.ZaGlobalConfig");
AjxPackage.require("zmailAdmin.status.model.ZaStatus");
AjxPackage.require("zmailAdmin.mta.model.ZaMTA");
AjxPackage.require("zmailAdmin.task.model.ZaTask");

// view
AjxPackage.require("zmailAdmin.home.view.ZaHomeXFormView");
AjxPackage.require("zmailAdmin.accounts.view.ZaAccountXFormView");
AjxPackage.require("zmailAdmin.accounts.view.ZaAccChangePwdXDlg");
AjxPackage.require("zmailAdmin.accounts.view.ZaEditAliasXDialog");
AjxPackage.require("zmailAdmin.accounts.view.ZaEditFwdAddrXDialog");
AjxPackage.require("zmailAdmin.accounts.view.ZaEditFpXDialog");
AjxPackage.require("zmailAdmin.accounts.view.ZaAccountListView");
AjxPackage.require("zmailAdmin.accounts.view.ZaNewAccountXWizard");
AjxPackage.require("zmailAdmin.accounts.view.MoveAliasXDialog");
AjxPackage.require("zmailAdmin.accounts.view.ReindexMailboxXDialog");
AjxPackage.require("zmailAdmin.accounts.view.DeleteAcctsPgrsDlg");
AjxPackage.require("zmailAdmin.servers.view.ZaServerVolumesListView");
AjxPackage.require("zmailAdmin.servers.view.ZaEditVolumeXDialog");
AjxPackage.require("zmailAdmin.servers.view.ZaProxyPortWarningXDialog");
AjxPackage.require("zmailAdmin.servers.view.ZaServerListView");
AjxPackage.require("zmailAdmin.servers.view.ZaServerMiniListView");
AjxPackage.require("zmailAdmin.servers.view.ZaFlushCacheXDialog");
AjxPackage.require("zmailAdmin.servers.view.ZaServerXFormView");
AjxPackage.require("zmailAdmin.adminext.view.ZaAdminExtListView");
AjxPackage.require("zmailAdmin.zimlets.view.ZaZimletListView");
AjxPackage.require("zmailAdmin.zimlets.view.ZaZimletXFormView");
AjxPackage.require("zmailAdmin.zimlets.view.ZaZimletDeployXWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaDomainListView");
AjxPackage.require("zmailAdmin.domains.view.ZaDomainXFormView");
AjxPackage.require("zmailAdmin.domains.view.ZaNewDomainXWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaDomainAliasWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaGALConfigXWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaAuthConfigXWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaTaskAuthConfigWizard");
AjxPackage.require("zmailAdmin.domains.view.ZaTaskAutoProvDialog");
AjxPackage.require("zmailAdmin.domains.view.ZaManualProvConfigDialog");
AjxPackage.require("zmailAdmin.domains.view.AddrACL_XFormItem");
AjxPackage.require("zmailAdmin.domains.view.ZaEditDomainAclXDialog");
AjxPackage.require("zmailAdmin.domains.view.ZaAddDomainAclXDialog");
AjxPackage.require("zmailAdmin.domains.view.ZaGalObjMiniListView");
AjxPackage.require("zmailAdmin.domains.view.ZaDomainAccountQuotaListView");
AjxPackage.require("zmailAdmin.cos.view.ZaCosListView");
AjxPackage.require("zmailAdmin.cos.view.ZaCosXFormView");
AjxPackage.require("zmailAdmin.cos.view.ZaNewCosXWizard");
AjxPackage.require("zmailAdmin.search.view.ZaSearchToolBar");
AjxPackage.require("zmailAdmin.search.view.ZaSearchField");
AjxPackage.require("zmailAdmin.search.view.ZaSearchListView");
AjxPackage.require("zmailAdmin.search.view.ZaSearchBuilderToolbarView");
AjxPackage.require("zmailAdmin.search.view.ZaSearchOptionView");
AjxPackage.require("zmailAdmin.search.view.ZaSearchBuilderView");
AjxPackage.require("zmailAdmin.search.view.ZaSearchOptionDialog");
AjxPackage.require("zmailAdmin.search.view.ZaSearchBubbleList");
AjxPackage.require("zmailAdmin.status.view.ZaServicesListView");
AjxPackage.require("zmailAdmin.statistics.view.ZaGlobalStatsView");
AjxPackage.require("zmailAdmin.statistics.view.ZaGlobalMessageVolumePage");
AjxPackage.require("zmailAdmin.statistics.view.ZaGlobalMessageCountPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaGlobalSpamActivityPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaGlobalAdvancedStatsPage");

AjxPackage.require("zmailAdmin.statistics.view.ZaServerStatsView");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerMessageVolumePage");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerMessageCountPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerSpamActivityPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerDiskStatsPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerMBXStatsPage");
AjxPackage.require("zmailAdmin.statistics.view.ZaServerSessionStatsPage");

AjxPackage.require("zmailAdmin.rp.view.ZaRetentionPolicyListView");
AjxPackage.require("zmailAdmin.rp.view.ZaRetentionPolicyDlg");

AjxPackage.require("zmailAdmin.globalconfig.view.GlobalConfigXFormView");
AjxPackage.require("zmailAdmin.accounts.view.ZaAccMiniListView");
AjxPackage.require("zmailAdmin.dl.view.ZaDLXFormView");
AjxPackage.require("zmailAdmin.dl.view.ZaNewDLXWizard");
AjxPackage.require("zmailAdmin.resource.view.ZaResourceXFormView");
AjxPackage.require("zmailAdmin.resource.view.ZaNewResourceXWizard");
AjxPackage.require("zmailAdmin.resource.view.ZaSignatureDlg");
AjxPackage.require("zmailAdmin.ZaZmailAdmin");
AjxPackage.require("zmailAdmin.helpdesk.view.ZaHelpView");
AjxPackage.require("zmailAdmin.helpdesk.view.ZaMWizView");
AjxPackage.require("zmailAdmin.mta.view.ZaQSummaryListView");
AjxPackage.require("zmailAdmin.mta.view.ZaQMessagesListView");
AjxPackage.require("zmailAdmin.mta.view.ZaMTAListView");
AjxPackage.require("zmailAdmin.mta.view.ZaMTAXFormView");
AjxPackage.require("zmailAdmin.mta.view.ZaMTAActionDialog");
AjxPackage.require("zmailAdmin.accounts.view.ZaAccountMemberOfListView");
AjxPackage.require("zmailAdmin.task.view.ZaTaskHeaderPanel");
AjxPackage.require("zmailAdmin.task.view.ZaTaskContentView");

AjxPackage.require("zmailAdmin.common.EmailAddr_FormItem");
