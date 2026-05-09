PN = "zigbee-rcp-apps"
SUMMARY = "Zigbee RCP example applications on i.MX boards for IWxxx 3-radios on SPI"
DESCRIPTION = "Zigbee RCP Applications"
LICENSE = "CLOSED"
LIC_FILES_CHKSUM = "file://LICENSE;md5=87109e44b2fda96a8991f27684a7349c"

S = "${UNPACKDIR}"
FILES:${PN} += "${bindir}"

DEPENDS += " readline zigbee-rcp-sdk"
RDEPENDS_${PN} += " readline "
inherit pkgconfig cmake

SRC_URI = ""
# Zigbee RCP applications
SRC_URI += "file://CMakeLists.txt"
SRC_URI += "file://zigbee_rcp_app.cmake"
SRC_URI += "file://examples/hello/"

