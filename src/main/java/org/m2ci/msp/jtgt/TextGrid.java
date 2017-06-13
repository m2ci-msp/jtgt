package org.m2ci.msp.jtgt;

import java.util.ArrayList;

/**
 * The textgrid container class
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public class TextGrid
{
    private ArrayList<Tier> _tiers;
    private String _filename;
    /*********************************************************************************************
     ** Accessors
     *********************************************************************************************/
    /**
     * Default constructor
     *
     */
    public TextGrid()
    {
	setTiers(new ArrayList<Tier>());
	setFilename(null);
    }

    /**
     * Constructor with a given filename
     *
     * @param filename the filename
     */
    public TextGrid(String filename)
    {
	setTiers(new ArrayList<Tier>());
	setFilename(filename);
    }

    /*********************************************************************************************
     ** Accessors
     *********************************************************************************************/
    /**
     * Getting the tiers
     *
     * @return the list of tiers
     */
    public ArrayList<Tier> getTiers() {
	return _tiers;
    }

    /**
     * Getting the filename
     *
     * @return the filename
     */
    public String getFilename() {
	return _filename;
    }

    /**
     * Associate the tiers to the textgrid
     *
     * @param tiers the tiers which will compose the textgrid
     */
    public void setTiers(ArrayList<Tier> tiers) {
	this._tiers = tiers;
    }

    /**
     * Define the filename
     *
     * @param filename the filename associated to the textgrid
     */
    public void setFilename(String filename) {
	this._filename = filename;
    }

    /*********************************************************************************************
     ** Insertion
     *********************************************************************************************/

    /**
     * Adding a tier at a specific position
     *
     * @param tier the tier to add
     * @param position the position to add the tier in
     */
    public void addTier(Tier tier, int position) {
	if (position < 0)
	    getTiers().add(tier);
	else
	    getTiers().add(position, tier);

    }

    /**
     * Adding the tier at the end of the tier list
     *
     * @param tier the tier to add
     */
    public void addTier(Tier tier) {
	addTier(tier, getTiers().size()-1);
    }

    /**
     * Adding
     *
     * @param tiers the tier list
     */
    public void addTiers(ArrayList<Tier> tiers)
    {
	for (Tier tier: tiers) {
	    addTier(tier);
	}
    }

    /*********************************************************************************************
     ** Deletion
     *********************************************************************************************/
    /**
     * Delete the tier knowing its name
     *
     * @param name the name of the tier
     */
    public void deleteTier(String name) {
	int pos = -1;
	int i = 0;
	while ((i<getTiers().size()) && (pos < 0)) {
	    if (getTiers().get(i).getName().equals(name))
		pos = i;

	    i++;
	}

	if (pos >= 0)
	    deleteTier(pos);
    }

    /**
     * Delete the tier given its position
     *
     * @param position the given position
     */
    public void deleteTier(int position) {
	getTiers().remove(position);
    }
}


/* TextGrid.java ends here */
