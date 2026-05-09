SUMMARY = "A path manipulation library for Python"
HOMEPAGE = "https://github.com/cgarciae/python_path"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

inherit pypi
BBCLASSEXTEND = "native"

# Add the following lines to your recipe
PYPI_PACKAGE = "python_path"
SRC_URI[sha256sum] = "b62d9aac1da4daee3f036ed088532cf8b68666d3aa103567dc22b6539316c8b3"

DEPENDS += " \
    python3-build-native \
    python3-installer-native \
    python3-setuptools-native \
    python3-wheel-native \
"

do_compile() {
    cd ${S}
    ${STAGING_BINDIR_NATIVE}/python3-native/python3 -m build --wheel --no-isolation
}

do_install() {
    cd ${S}
    ${STAGING_BINDIR_NATIVE}/python3-native/python3 -m installer --destdir ${D} dist/*.whl
}
