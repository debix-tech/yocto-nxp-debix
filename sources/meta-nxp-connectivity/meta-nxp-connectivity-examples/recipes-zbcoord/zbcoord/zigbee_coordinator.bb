PN = "zigbee-coordinator"
SUMMARY = "Zigbee Coordinator Application"
DESCRIPTION = "This recipe builds the Zigbee Coordinator application from the specified Git repository."
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://github.com/nxp-mcuxpresso/mcuxsdk-middleware-zigbee.git;branch=release/24.12.00-pvw2;protocol=https"
SRC_URI += "file://0001-fix-imx-build-error.patch"
SRCREV = "24281227192e4b2e090c5e1a4bedae55a9a537c4"

python() {
    http_proxy = d.getVar("http_proxy") or ""
    https_proxy = d.getVar("https_proxy") or ""

    d.setVar("MY_HTTP_PROXY", http_proxy)
    d.setVar("MY_HTTPS_PROXY", https_proxy)
}

S = "${WORKDIR}/git"

inherit cmake

DEPENDS += " mbedtls "
RDEPENDS:${PN} += " mbedtls "

do_configure() {
    local http_proxy="${MY_HTTP_PROXY}"
    local https_proxy="${MY_HTTPS_PROXY}"

    if [ -n "$http_proxy" ]; then
        export http_proxy
    fi
    if [ -n "$https_proxy" ]; then
        export https_proxy
    fi
    cd ${S}/examples/zigbee_coordinator/build_linux
    cmake ./ -DMACHINE_TYPE=imx8 -DCONFIG_MBEDTLS_SOURCE=GIT -DENABLE_TESTING=OFF -DENABLE_PROGRAMS=OFF
}

do_compile() {
    cd ${S}/examples/zigbee_coordinator/build_linux
    oe_runmake
}

do_install() {
    install -d ${D}/usr/bin
    install -m 0755 ${S}/examples/zigbee_coordinator/build_linux/zb_coord_linux ${D}/usr/bin/
}

FILES_${PN} += "/usr/bin/zb_coord_linux"
