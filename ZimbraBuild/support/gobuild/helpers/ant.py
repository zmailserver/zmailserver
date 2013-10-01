# Copyright 2008 VMware, Inc.  All rights reserved. -- VMware Confidential

"""
Helpers for ant-based targets.
"""

import os

import helpers.target

class AntHelper:
   """
   Helper class for targets that build with ant.
   """
   def _Command(self, hosttype, target, antversion='1.7.0', **flags):
      """
      Return a dictionary representing a command to invoke ant with
      standard antflags.
      """

      def q(s):
         return '"%s"' % s

      defaults = {
         'GOBUILD_OFFICIAL_BUILD' :       '1',
         'GOBUILD_AUTO_COMPONENTS':       'false',
         'OBJDIR'                 :       q('%(buildtype)'),
         'RELTYPE'                :       q('%(releasetype)'),
         'BUILD_NUMBER'           :       q('%(buildnumber)'),
         'PRODUCT_BUILD_NUMBER'   :       q('%(productbuildnumber)'),
         'CHANGE_NUMBER'          :       q('%(changenumber)'),
         'BRANCH_NAME'            :       q('%(branch)'),
         'PUBLISH_DIR'            :       q('%(buildroot)/publish'),
         'BUILDLOG_DIR'           :       q('%(buildroot)/logs'),
         'REMOTE_COPY_SCRIPT'     :       q('%(gobuildc) %(buildid)'),
      }

      # Add a GOBUILD_*_ROOT flag for every component we depend on.
      if hasattr(self, 'GetComponentDependencies'):
         for d in self.GetComponentDependencies():
            d = d.replace('-', '_')
            defaults['GOBUILD_%s_ROOT' % d.upper()] = \
                              '%%(gobuild_component_%s_root)' % d

      # Override the defaults above with the options passed in by
      # the client.
      defaults.update(flags)

      # Choose ant
      if hosttype.startswith('windows'):
         tcroot = os.environ.get('TCROOT', 'C:/TCROOT-not-set')
         antcmd = '%s/noarch/ant-%s/bin/ant.bat' % (tcroot, antversion)
      else:
         antcmd = '/build/toolchain/noarch/ant-%s/bin/ant' % antversion

      # Create the command line to invoke ant
      cmd = '%s %s ' % (antcmd, target)
      for k in sorted(defaults.keys()):
         v = defaults[k]
         cmd += ' -D' + str(k)
         if v is not None:
            cmd += '=' + str(v)

      return cmd

