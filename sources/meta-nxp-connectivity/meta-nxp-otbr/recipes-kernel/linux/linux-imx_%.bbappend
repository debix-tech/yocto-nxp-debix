FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

def get_arm_arch(d):
    for arg in (d.getVar('TUNE_FEATURES') or '').split():
        if arg == "cortexa7":
            return ''
        if arg == "armv8a":
            return "file://kernel-config/0001-Add-Matter-and-OTBR-configs.cfg"
    return "file://kernel-config/0001-Add-Matter-and-OTBR-configs.cfg"

SRC_URI += "file://patches/0002-Enable-otbr-and-firewall-configs-for-imx6-7.patch"
SRC_URI += "file://patches/Disable-Power-Save-mode-for-BT.patch"

SRC_URI += "${@get_arm_arch(d)}"

do_patch:append() {
    echo "Starting to copy DTS files after patching."
    if [ -n "${DTS_FILE}" ]; then
        for i in ${DTS_FILE}; do
            if [ -f ${i} ]; then
                echo "Copying ${i} to ${S}/arch/arm64/boot/dts/freescale"
                cp ${i} ${S}/arch/arm64/boot/dts/freescale/
            fi
        done
    fi
}
