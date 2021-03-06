# T.R.Marsh, Warwick.

# Makefile for Usdriver, requires that 'util' and 'ultracam'
# have already been setup.
#
# List of possible targets:
#
# make           -- builds class files
# make install   -- install a jar file of the classes
# make clean     -- cleans out the class files and other junk
# make dist      -- makes a tar file for distribution (me only)
# make uninstall -- removes installed software

# PACKAGE      = name of package
# MAIN         = name of program
# VERSION      = version number
# TOP_DIR      = path to top-level directory with generic Makefile.include
# BUILD_DIR    = where files generated during the build are stored
# PACKAGE_PATH = path to dig down to where classes are stored

PACKAGE      = usdriver
MAIN         = Usdriver
VERSION      = 2
TOP_DIR      = ../..
BUILD_DIR    = ../build
PACKAGE_PATH = warwick/marsh/ultracam/usdriver

INSTALL_DIR         = $(TRM_JAVA)/$(PACKAGE)-$(VERSION)

DIST_DIR            = /tmp/$(PACKAGE)-$(VERSION)

TEMPLATE_DIR        = $(INSTALL_DIR)/templates
APPLICATION_DIR     = $(INSTALL_DIR)/applications
LOG_FILE_DIR        = $(INSTALL_DIR)/logs
HTML_DIR            = $(INSTALL_DIR)/html
CONFIG_FILE         = usdriver.conf

JAVA_PACKAGE_PATH   = $(subst /,.,$(PACKAGE_PATH))

JAR_FILE     = $(PACKAGE).jar

PWD          = `pwd`

TEST_NAME    = $(shell echo $(PWD)/$(BUILD_DIR)/bin/$(PACKAGE) | sed -e 's%[^/]*/\.\./%%g')

# make sure this comes first
ifdef TRM_JAVA

build:  all ../html/index.html $(BUILD_DIR)/lib/$(JAR_FILE) $(BUILD_DIR)/bin/$(PACKAGE) $(BUILD_DIR)/bin/$(CONFIG_FILE)
	@echo " "
	@echo " "
	@echo "You can now test whether the software works by running the script '$(TEST_NAME)'"
	@echo "If it is OK, then you can 'make install'"
	@echo " "
else

build:
	@echo " "
	@echo "ERROR: You must first define the environment variable TRM_JAVA to point to the installation directory"
	@echo " "

endif

# Files need specifying to get right order of compilation
FILES = SingleWindows.java WindowPairs.java FilterChooser.java SlideController.java FilterWheel.java WheelController.java Usdriver.java

# include generic stuff
include $(TOP_DIR)/Makefile.include

# Special bit to set version number
Usdriver.java: Makefile
	sed -e 's/\"ULTRASPEC window creator and driver.*\"/\"ULTRASPEC window creator and driver, version $(VERSION)\"/' $@ > /tmp/$@
	mv /tmp/$@ $@

# Corrects the version of the index file
../html/index.html: Makefile
	sed -e  's%The ULTRACAM Java client/server.*<%The ULTRACAM Java client/server, version $(VERSION)<%g' $@ > /tmp/index.html
	mv /tmp/index.html $@

# Makes configuration files
$(BUILD_DIR)/bin/$(CONFIG_FILE): $(CONFIG_FILE) Makefile
	sed -e 's%^APP_DIRECTORY.*%APP_DIRECTORY         = $(APPLICATION_DIR)%' \
	    -e 's%^TEMPLATE_DIRECTORY.*%TEMPLATE_DIRECTORY    = $(TEMPLATE_DIR)%' \
	    -e 's%^LOG_FILE_DIRECTORY.*%LOG_FILE_DIRECTORY    = $(LOG_FILE_DIR)%' $< > $@

# makes script for running the program
$(BUILD_DIR)/bin/$(PACKAGE): Makefile
	-mkdir -p $(BUILD_DIR)/bin
	@echo '#!/bin/csh -f' > $@
	@echo '########################################################################' >> $@
	@echo '#                                                                      #' >> $@
	@echo '# This file has been automatically generated by "gmake"                #' >> $@
	@echo '# using settings in reduce/src/Makefile                                #' >> $@
	@echo '#                                                                      #' >> $@
	@echo '########################################################################' >> $@
	@echo ' ' >> $@
	@echo 'cd `dirname $$0`' >> $@
	@echo ' ' >> $@
	@echo 'set JAVA="$(JAVA) -Dgnu.io.rxtx.SerialPorts=/dev/filterwheel"' >> $@
	@echo ' ' >> $@
	@echo 'if( $$?UDRIVERPATH ) then' >> $@
	@echo '  set UDRIVERPATH=../lib/$(JAR_FILE):$$UDRIVERPATH' >> $@
	@echo 'else' >> $@
	@echo '  set UDRIVERPATH=../lib/$(JAR_FILE)' >> $@
	@echo 'endif' >> $@
	@echo ' ' >> $@
	@echo 'if( $$#argv == 1 ) then' >> $@
	@echo '   $${JAVA} -DCONFIG_FILE=$$1 -classpath $${UDRIVERPATH} $(JAVA_PACKAGE_PATH).$(MAIN)' >> $@
	@echo 'else' >> $@
	@echo '  $${JAVA} -DCONFIG_FILE=usdriver.conf -classpath $${UDRIVERPATH} $(JAVA_PACKAGE_PATH).$(MAIN)' >> $@
	@echo 'endif' >> $@
	@echo ' ' >> $@
	@echo 'exit' >> $@
	@echo Created $@
	@chmod +x $@

# This could be improved as at the moment it will copy ALL classes into the jar file.

# Get rid of subclasses with dollar signs as they just cause problems; if any of them
# have been updated, then the containing class file will have been updated too
#JAR_DEPS := $(shell find $(CLASS_DIR) -name "*.class" | grep -v '\$$')
JAR_DEPS := $(wildcard *.java)

$(BUILD_DIR)/lib/$(JAR_FILE): $(JAR_DEPS)
	-mkdir -p $(BUILD_DIR)/lib
	$(JAR) cf $@ -C $(CLASS_DIR) .
	@echo "Made jar file $@"

# install as a script with classes in a jar file
ifdef TRM_JAVA

install: build $(INSTALL_DIR)/lib/$(JAR_FILE) $(INSTALL_DIR)/bin/$(PACKAGE) $(INSTALL_DIR)/bin/$(CONFIG_FILE)
	cd $(INSTALL_DIR)/..; rm $(PACKAGE); ln -sf $(PACKAGE)-$(VERSION) $(PACKAGE) 
	-mkdir -p $(TEMPLATE_DIR)
	-cp ../templates/*.xml $(TEMPLATE_DIR)/.
	-mkdir -p $(APPLICATION_DIR)
	-mkdir -p $(HTML_DIR)
	-cp ../html/*    $(HTML_DIR)/.
	-cp usdriver.conf $(HTML_DIR)/.
	-mkdir -p $(LOG_FILE_DIR)
	@echo " "
	@echo " "
	@echo "The software can be started by running the script '$(INSTALL_DIR)/bin/$(PACKAGE)'"
	@echo " "

uninstall:
	rm -r $(INSTALL_DIR);
	@echo " "
	@echo "The installed software has been removed with the exception of the link $(INSTALL_DIR)/$(PACKAGE)"
	@echo "If you don't use the link delete it, otherwise you should attach it to a different version of the software."
	@echo " "

else

install: 
	@echo " "
	@echo "ERROR: You must first define the environment variable TRM_JAVA to point to the installation directory"
	@echo " "

uninstall:
	@echo " "
	@echo "ERROR: You must first define the environment variable TRM_JAVA to point to the installation directory"
	@echo " "

endif

# copy over the jar file from the build
$(INSTALL_DIR)/lib/$(JAR_FILE): $(BUILD_DIR)/lib/$(JAR_FILE)
	-mkdir -p $(INSTALL_DIR)/lib
	cp $< $@

# copy over the script & config file from the build
$(INSTALL_DIR)/bin/$(PACKAGE): $(BUILD_DIR)/bin/$(PACKAGE)
	-mkdir -p $(INSTALL_DIR)/bin
	cp $< $@
	chmod +x $@
	-cp $(BUILD_DIR)/bin/$(CONFIG_FILE) $(INSTALL_DIR)/bin/$(CONFIG_FILE)

clean_extra:
	-\rm -rf $(BUILD_DIR)
	-\rm -rf $(DIST_DIR)

# Distribution
dist:
	-\rm -rf $(DIST_DIR) 
	mkdir -p $(DIST_DIR) 
	cd ../..; cp -r util $(DIST_DIR); cp -r ultracam $(DIST_DIR); cp -r usdriver $(DIST_DIR)
	@printf \
	'# To install, define the environment variable TRM_JAVA to point at\
	\n# the directory you wish to install under then type make and, if\
	\n# it works, make install.\
	\n\
	\nSUBDIRS = util ultracam usdriver' > $(DIST_DIR)/Makefile
	cat $(TOP_DIR)/Makefile.dist >> $(DIST_DIR)/Makefile; cp $(TOP_DIR)/Makefile.include $(DIST_DIR)
	cd $(DIST_DIR)/..; tar cvfz $(PACKAGE)-$(VERSION).tar.gz --exclude='*/build/*' --exclude='*/.git' $(PACKAGE)-$(VERSION)
	cd $(DIST_DIR)/..; scp $(PACKAGE)-$(VERSION).tar.gz $(WEB_SERVE):$(WEB_PATH)/software/.
	-ssh $(WEB_SERVE) "rm -r $(WEB_PATH)/ultracam/usdriver"
	scp -r ../html $(WEB_SERVE):$(WEB_PATH)/ultracam/usdriver
	ssh $(WEB_SERVE) "cd $(WEB_PATH)/software ; ln -sf $(PACKAGE)-$(VERSION).tar.gz $(PACKAGE).tar.gz"



