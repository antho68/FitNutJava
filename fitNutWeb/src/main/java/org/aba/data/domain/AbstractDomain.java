package org.aba.data.domain;

public abstract class AbstractDomain
{
    private Long pk;
    private boolean editable = true;
    private boolean copyable = true;
    private boolean deletable = true;
    private String editableMsg = null;
    private String deletableMsg = null;
    private String copyableMsg = null;

    protected AbstractDomain()
    {
    }

    public boolean isEditable()
    {
        return this.editable;
    }

    public void setEditable(boolean editable)
    {
        this.editable = editable;
    }

    public boolean isDeletable()
    {
        return this.deletable;
    }

    public void setDeletable(boolean deletable)
    {
        this.deletable = deletable;
    }

    public String getEditableMsg()
    {
        return this.editableMsg == null ? "Modifier" : this.editableMsg;
    }

    public void setEditableMsg(String editableMsg)
    {
        this.editableMsg = editableMsg;
    }

    public String getDeletableMsg()
    {
        return this.deletableMsg == null ? "Supprimer" : this.deletableMsg;
    }

    public void setDeletableMsg(String deletableMsg)
    {
        this.deletableMsg = deletableMsg;
    }

    public boolean isCopyable()
    {
        return this.copyable;
    }

    public void setCopyable(boolean copyable)
    {
        this.copyable = copyable;
    }

    public String getCopyableMsg()
    {
        return this.copyableMsg == null ? "Copier" : this.copyableMsg;
    }

    public void setCopyableMsg(String copyableMsg)
    {
        this.copyableMsg = copyableMsg;
    }

    public Long getPk()
    {
        return pk;
    }

    public void setPk(Long pk)
    {
        this.pk = pk;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        else if (obj == null)
        {
            return false;
        }
        else if (this.getClass() != obj.getClass())
        {
            return false;
        }
        else
        {
            AbstractDomain other = (AbstractDomain) obj;
            if (this.pk == null)
            {
                if (other.getPk() != null)
                {
                    return false;
                }
            }
            else if (!this.getPk().equals(other.getPk()))
            {
                return false;
            }

            return true;
        }
    }
}

