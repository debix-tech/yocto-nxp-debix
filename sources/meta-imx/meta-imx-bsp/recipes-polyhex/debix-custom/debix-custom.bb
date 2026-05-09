DESCRIPTION = "polyhex debix update files"
SECTION = "bin"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = " \
        file://rootfs \
	"

#RDEPENDS_${PN} += "bash"
RDEPENDS:${PN} += " bash python3-core "
#EXTRA_OEMAKE += "SBINDIR=${sbindir}"
EXTRA_OEMAKE = " SBINDIR='${sbindir}' "
INSANE_SKIP:${PN} += "file-rdeps"

S = "${WORKDIR}/git"

do_install() {
        install -d ${D}/etc
        install -d ${D}/usr

	#cp -r -f --preserve=mode,ownership ${S}/rootfs/* ${D}/.
	cp -r -f ${S}/../sources-unpack/rootfs/* ${D}/.
}

INSANE_SKIP:${PN} += " ldflags"
INHIBIT_PACKAGE_STRIP = "1"
INHIBIT_SYSROOT_STRIP = "1"
SOLIBS = ".so"
FILES_SOLIBSDEV = ""


FILES:${PN} += "\
        /* \
        /etc/* \
        /usr/* \
"

