SUMMARY = "NNStreamer-Edge library"
DESCRIPTION = "Remote source nodes for NNStreamer pipelines without GStreamer dependencies"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=095e13fef457e259d3bc155d0ed859f1"

DEPENDS = "\
    gtest \
"

NNS_EDGE_SRC ?= "git://github.com/nnstreamer/nnstreamer-edge.git;protocol=https"
SRCBRANCH = "lts/0.2.4.b"
SRCREV = "2bf50d57f0f8d856ae38cf82b0a0f3746f46a08a"
SRC_URI = "${NNS_EDGE_SRC};branch=${SRCBRANCH}"

S = "${WORKDIR}/git"

inherit cmake pkgconfig

EXTRA_OECMAKE =  " \
    -DENABLE_TEST=ON \
"
