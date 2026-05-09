BUILD_TRUSTY = "${@bb.utils.contains('MACHINE_FEATURES', 'trusty', 'true', 'false', d)}"
PACKAGECONFIG[trusty] = "SPD=trusty"

PACKAGECONFIG:append = " ${@bb.utils.filter('MACHINE_FEATURES', 'trusty', d)}"
ANNOTATED_NAME:append = "${@bb.utils.contains('PACKAGECONFIG', 'trusty', '-trusty', '', d)}"
