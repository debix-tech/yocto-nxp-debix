FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

PATCHTOOL = "git"
SRC_URI += "${@bb.utils.contains_any('MACHINE', "imx8mmevk-matter", 'file://0001-Add-Trusty-prebuilt-binary-for-project-Matter.patch', '', d)}"
SRC_URI += "${@bb.utils.contains_any('MACHINE', "imx8mpevk-matter", 'file://0001-Add-i.MX8MP-Trusty-OS-binary-for-Matter.patch', '', d)}"
