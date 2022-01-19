function pdbViewer(pdb_file) {
    var Info = {
        color: "#FFFFFF",
        height: 1000,
        width: 1000,
        script: "load " + "/data/" + pdb_file,
        j2sPath: "../../jsmol/j2s",
        align: "right",
    };

    jmolApplet0 = Jmol.getApplet("myJmol", Info);
// Set initial zoom level
    let initzoom = 80;
    let zoom = initzoom
    Jmol.script(jmolApplet0, `background white; spin off; zoom ${initzoom}`);
}